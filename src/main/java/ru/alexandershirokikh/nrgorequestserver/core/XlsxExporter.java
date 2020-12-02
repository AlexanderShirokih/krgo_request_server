package ru.alexandershirokikh.nrgorequestserver.core;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.alexandershirokikh.nrgorequestserver.models.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XlsxExporter implements IExporterService {
    private final List<RequestSet> worksheets;

    public XlsxExporter(List<RequestSet> worksheets) {
        this.worksheets = worksheets;
    }

    @Override
    public void export(OutputStream outputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        for (var worksheet : worksheets) {
            writeWorksheet(worksheet, workbook.createSheet(worksheet.getName()));
        }

        workbook.write(outputStream);
    }

    private void writeWorksheet(RequestSet worksheet, XSSFSheet sheet) {
        Stream<Employee> membersStream = worksheet.requireMembersEmployee();
        long membersCount = worksheet.requireMembersEmployee().count();
        String members = membersCount == 0L ? "--" : membersStream.map(Employee::getName).collect(Collectors.joining(", "));

        createStringCell(sheet.createRow(0), 0, "Призводитель работ: " + worksheet.requireMainEmployee().getName());
        createStringCell(sheet.createRow(1), 0, membersCount > 1L
                ? "Члены бригады: " + members
                : "Член бригады: " + members);

        writeRequestsHeader(sheet.createRow(2));

        final var requests = worksheet.getRequests();
        for (int i = 0; i < requests.size(); i++) {
            writeRequest(requests.get(i), i + 1, sheet.createRow(i + 3));
        }
    }

    private void writeRequestsHeader(XSSFRow row) {
        createStringCell(row, 0, "№");
        createStringCell(row, 1, "Л/С");
        createStringCell(row, 2, "Потребитель");
        createStringCell(row, 3, "Адрес");
        createStringCell(row, 4, "ПУ");
        createStringCell(row, 5, "Тип заявки");
        createStringCell(row, 6, "Дополнительно");
    }

    private void writeRequest(Request request, int position, XSSFRow row) {
        var r = new PrintableRequest(request);

        createStringCell(row, 0, String.valueOf(position));
        createStringCell(row, 1, r.getAccountId());
        createStringCell(row, 2, r.getName());
        createStringCell(row, 3, r.getAddress());
        createStringCell(row, 4, r.getCounterInfo());
        createStringCell(row, 5, r.getReqType());
        createStringCell(row, 6, r.getAdditions());
    }

    private void createStringCell(XSSFRow row, int column, String value) {
        row.createCell(column, CellType.STRING).setCellValue(value);
    }
}
