package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.CountingOutputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.core.PdfExporter;
import ru.alexandershirokikh.nrgorequestserver.data.dao.RequestSetRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.EmployeeAssignmentDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestSetDTO;
import ru.alexandershirokikh.nrgorequestserver.models.EmployeeAssignmentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExporterService {

    private final RequestService requestService;
    private final RequestSetRepository requestSetRepository;

    /**
     * Checks given worksheets and returns validation errors for all of them.
     */
    public Map<Long, List<String>> checkWorksheetStatus(List<Long> worksheetsIds) {
        return requestSetRepository.findAllById(worksheetsIds)
                .stream()
                .collect(Collectors.toMap(RequestSetDTO::getId, this::validateWorksheet));
    }

    private List<String> validateWorksheet(RequestSetDTO worksheet) {
        ArrayList<String> errors = new ArrayList<>();

        final var assignments = worksheet.getAssignments();

        if (assignments == null || assignments.isEmpty()) {
            errors.add("Нет сотрудников для выполнения работ");
        } else {
            final var types = assignments.stream()
                    .map(EmployeeAssignmentDTO::getAssignmentType)
                    .collect(Collectors.toUnmodifiableSet());

            if (!types.contains(EmployeeAssignmentType.CHIEF)) {
                errors.add("Не выбран выдающий задание");
            }

            if (!types.contains(EmployeeAssignmentType.MAIN)) {
                errors.add("Не выбран производитель работ");
            }
        }

        final var requests = worksheet.getRequests();

        if (requests == null || requests.isEmpty()) {
            errors.add("Нет заявок для печати");
        } else if (requests.size() > 18) {
            errors.add("Слишком много заявок для печати на одном листе");
        }

        if (worksheet.getDate() == null) {
            errors.add("Не выбрана дата");
        }

        return errors;
    }

    public static class SizedResource {
        final long size;
        final Resource resource;

        public SizedResource(long size, Resource resource) {
            this.size = size;
            this.resource = resource;
        }

        public long getSize() {
            return size;
        }

        public Resource getResource() {
            return resource;
        }
    }

    /**
     * Builds PDF Document from given worksheets
     */
    public SizedResource buildPdf(List<Long> worksheetsIds) throws IOException {
        final var worksheets = requestSetRepository.findAllById(worksheetsIds);

        if (hasNoErrors(worksheets)) {
            final var convertedWorksheets = requestService.convertEntities(worksheets);
            PdfExporter pdfExporter = new PdfExporter(convertedWorksheets);

            File temp = File.createTempFile("pdf", null);
            temp.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(temp);
            CountingOutputStream cos = new CountingOutputStream(fos);
            pdfExporter.export(cos);

            long size = cos.getBytesWritten();
            return new SizedResource(size, new InputStreamResource(new FileInputStream(temp)));
        }

        return null;
    }

    /**
     * Builds XLSX Worksheet from given worksheets
     */
    public SizedResource buildXlsx(List<Long> worksheetsIds) {
        throw new RuntimeException();
    }

    private boolean hasNoErrors(List<RequestSetDTO> worksheets) {
        return worksheets
                .stream()
                .allMatch(requestSetDTO -> validateWorksheet(requestSetDTO).isEmpty());
    }
}
