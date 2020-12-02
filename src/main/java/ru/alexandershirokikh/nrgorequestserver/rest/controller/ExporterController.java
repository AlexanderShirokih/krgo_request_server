package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.data.service.ExporterService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("export/")
@RequiredArgsConstructor
public class ExporterController {

    private final ExporterService exporterService;

    /**
     * Validates given worksheets and returns list of errors.
     */
    @GetMapping("status")
    public Map<Long, List<String>> getStatus(@RequestParam("ids") String idsList) {
        return exporterService.checkWorksheetStatus(decodeWorksheetIds(idsList));
    }

    /**
     * Builds PDF Document for given worksheets.
     * Returns 422 Unprocessable Entity if any worksheet has error
     */
    @GetMapping("pdf")
    public ResponseEntity<Resource> buildPdf(@RequestParam("ids") String idsList) {
        try {
            ExporterService.SizedResource resource = exporterService.buildPdf(decodeWorksheetIds(idsList));
            if (resource == null) {
                return ResponseEntity.unprocessableEntity().build();
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .contentLength(resource.getSize())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"requests_" + String.join(",", idsList) + ".pdf\"")
                    .body(resource.getResource());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Builds XLSX Document for given worksheets.
     * Returns 422 Unprocessable Entity if any worksheet has error
     */
    @GetMapping("xlsx")
    public ResponseEntity<Resource> buildXlsx(@RequestParam("ids") String idsList) {
        try {
            ExporterService.SizedResource resource = exporterService.buildXlsx(decodeWorksheetIds(idsList));
            if (resource == null) {
                return ResponseEntity.unprocessableEntity().build();
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"doc_" + String.join(",", idsList) + ".xlsx\"")
                    .body(resource.getResource());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private static List<Long> decodeWorksheetIds(String idsList) {
        return Arrays
                .stream(idsList.split(","))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Gets a list of available printer services
     */
    @GetMapping("print")
    public List<String> getPrintersList() {
        return exporterService.listPrinters();
    }

    @GetMapping("print/{printerName}")
    public ResponseEntity<Void> printDocument(@PathVariable("printerName") String printerName,
                                              @RequestParam("ids") String idsList,
                                              @RequestParam(value = "noLists", defaultValue = "false", required = false) Boolean noLists) {
        try {
            exporterService.printWorksheets(printerName, noLists, decodeWorksheetIds(idsList));
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
