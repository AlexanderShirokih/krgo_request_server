package ru.alexandershirokikh.nrgorequestserver.core;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.printing.PDFPageable;
import ru.alexandershirokikh.nrgorequestserver.models.*;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for exporting worksheets to PDF document
 */
public class PdfExporter implements IExporterService {

    private final ClassLoader loader = PdfExporter.class.getClassLoader();
    private final List<RequestSet> worksheets;

    public PdfExporter(List<RequestSet> worksheets) {
        this.worksheets = worksheets;
    }


    /**
     * Creates PDF document and sends them to printer
     *
     * @throws IOException      on document creation errors
     * @throws PrinterException on any printing errors
     */
    public void print(PrintService printer, boolean noLists) throws IOException, PrinterException {
        PDDocument ordersDoc = new PDDocument();

        writeOrders(ordersDoc, worksheets, PDType0Font.load(
                ordersDoc,
                loader.getResourceAsStream("fonts/Roboto-Regular.ttf"), false));


        PDDocument listsDoc = null;
        if (!noLists) {
            listsDoc = new PDDocument();
            writeLists(
                    listsDoc, worksheets, PDType0Font.load(
                            listsDoc,
                            loader.getResourceAsStream("fonts/Roboto-Regular.ttf"), false
                    )
            );
        }

        PrinterJob printerJob = PrinterJob.getPrinterJob();

        printerJob.setPageable(new PDFPageable(ordersDoc));
        printerJob.setJobName("Распоряжения");
        printerJob.setPrintService(printer);

        HashPrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        attributeSet.add(Sides.TWO_SIDED_SHORT_EDGE);

        printerJob.print(attributeSet);

        if (listsDoc != null) {
            PrinterJob printerJob2 = PrinterJob.getPrinterJob();
            printerJob2.setPageable(new PDFPageable(listsDoc));
            printerJob.setJobName("Списки работ");
            printerJob.setPrintService(printer);
            printerJob.print();
        }
    }

    /**
     * Creates PDF document and stores it to outputStream.
     *
     * @throws IOException on any creation error
     */
    @Override
    public void export(OutputStream outputStream) throws IOException {
        PDDocument target = new PDDocument();
        PDFont font = PDType0Font.load(
                target,
                loader.getResourceAsStream("fonts/Roboto-Regular.ttf"), false
        );

        writeOrders(target, worksheets, font);
        writeLists(target, worksheets, font);

        target.save(outputStream);
        target.close();
    }

    private void writeOrders(PDDocument target, List<RequestSet> worksheets, PDFont font) throws IOException {
        PDDocument orderTemplate = PDDocument.load(loader.getResourceAsStream("templates/order.pdf"));

        for (var worksheet : worksheets) {
            writeFirstPage(target, target.importPage(orderTemplate.getPage(0)), font, worksheet);
            writeSecondPage(target, target.importPage(orderTemplate.getPage(1)), font, worksheet);
        }
    }

    private void writeFirstPage(PDDocument target, PDPage page, PDFont font, RequestSet worksheet) throws IOException {
        try (PDPageContentStream content = new PDPageContentStream(target, page, PDPageContentStream.AppendMode.APPEND, false)) {
            writeHeading(content, worksheet, font);
            writeFirstPageRequests(content, worksheet.getRequests(), font);
        }
    }

    private void writeSecondPage(PDDocument target, PDPage page, PDFont font, RequestSet worksheet) throws IOException {
        try (PDPageContentStream content = new PDPageContentStream(target, page, PDPageContentStream.AppendMode.APPEND, false)) {
            writeSecondPageRequests(content, worksheet.getRequests(), font);
            writeTail(content, worksheet, font);
        }
    }

    private void writeHeading(PDPageContentStream content, RequestSet worksheet, PDFont font) throws IOException {
        String members = worksheet.requireMembersEmployee()
                .map(Employee::getNameWithGroup)
                .collect(Collectors.joining(", "));

        writeTextAt(content, 174.0f, 435.0f, worksheet.requireMainEmployee().getNameWithGroup(), font);
        writeTextAt(content, 164.0f, 409.0f, members, font);

        writeMultiline(
                content,
                278.0f,
                383.0f,
                60.0f,
                370.0f,
                84,
                String.join(", ", worksheet.getWorkTypes()) + " согласно бланка распоряжения номер № ",
                font
        );

        writeTextAt(content, 392.0f, 316.0f, worksheet.getDay(), font);
        writeTextAt(content, 455.0f, 316.0f, worksheet.getMonth(), font);
        writeTextAt(content, 488.0f, 316.0f, worksheet.getYear(), font);
        writeTextAt(content, 166.0f, 302.0f, worksheet.getFullDate(), font);
        writeTextAt(content, 466.0f, 302.0f, worksheet.requireChiefEmployee().getNameWithGroup(), font);
        writeTextAt(content, 320.0f, 276.0f, worksheet.requireChiefEmployee().getNameWithGroup(), font);
        writeTextAt(content, 675.0f, 276.0f, worksheet.requireMainEmployee().getNameWithGroup(), font);

    }

    private void writeTail(PDPageContentStream content, RequestSet worksheet, PDFont font) throws IOException {
        writeTextAt(content, 140.0f, 88.0f, worksheet.requireChiefEmployee().getNameWithPosition(), font);
        writeTextAt(content, 392.0f, 88.0f, worksheet.getDay(), font);
        writeTextAt(content, 465.0f, 88.0f, worksheet.getMonth(), font);
        writeTextAt(content, 508.0f, 88.0f, worksheet.getYear(), font);
    }

    private void writeFirstPageRequests(PDPageContentStream content, List<Request> requests, PDFont font) throws IOException {
        writeRequests(content, 159.0f, requests.stream().limit(4), 1, 30.2f, font);
    }

    private void writeSecondPageRequests(PDPageContentStream content, List<Request> requests, PDFont font) throws IOException {
        writeRequests(content, 537.0f, requests.stream().skip(4).limit(14), 5, 29.55f, font);
    }

    private void writeRequests(
            PDPageContentStream content,
            float baseY,
            Stream<Request> requests,
            int startPosition,
            float lineSpacing,
            PDFont font
    ) throws IOException {
        final var requestsCollections = requests.collect(Collectors.toList());
        for (int i = 0; i < requestsCollections.size(); i++) {
            writeSingleRequest(
                    content,
                    60f,
                    baseY - i * lineSpacing,
                    font,
                    i + startPosition,
                    requestsCollections.get(i).getAccountInfo()
            );
        }
    }

    private void writeSingleRequest(
            PDPageContentStream content,
            float baseX,
            float baseY,
            PDFont font,
            int position,
            Account address
    ) throws IOException {
        writeTextAt(content, baseX, baseY, String.valueOf(position), font, 8.0f);
        writeMultiline(content, baseX + 21f, baseY, baseX + 21f, baseY - 14f, 32, address.getFullAddress(), font, 7.0f, 0.0f);
        final String techActions =
                "1) Отключить нагрузку; 2) Проверить фазировку;\n" +
                        "3) Отключить электроустановку; 4) Проверить\n" +
                        "отсутствие напряжения; 5) Применить\n" +
                        "основные и дополнительные средства защиты\n";

        writeMultiline(
                content,
                baseX + 140f,
                baseY + 5f,
                baseX + 140f,
                baseY - 2f,
                300,
                techActions,
                font,
                6.0f,
                7.0f
        );
    }

    private void writeLists(PDDocument target, List<RequestSet> worksheets, PDFont font) throws IOException {
        PDDocument listTemplate = PDDocument.load(loader.getResourceAsStream("templates/list.pdf"));

        for (var worksheet : worksheets) {
            PDPage page = target.importPage(listTemplate.getPage(0));
            try (PDPageContentStream content = new PDPageContentStream(target, page, PDPageContentStream.AppendMode.APPEND, false)) {
                writeListHeading(content, worksheet, font);
                writeListContent(content, worksheet, font);
            }
        }
    }

    private void writeListHeading(PDPageContentStream content, RequestSet worksheet, PDFont font) throws IOException {
        writeTextAt(
                content,
                30.0f,
                795.0f,
                "Приложение к распоряжению № ____________ от " + worksheet.getFullDate(),
                font,
                12.0f
        );
        writeTextAt(
                content,
                34.0f,
                775.0f,
                "Призводитель работ: " + worksheet.requireMainEmployee().getName(),
                font,
                10.0f
        );

        Stream<Employee> membersStream = worksheet.requireMembersEmployee();
        long membersCount = worksheet.requireMembersEmployee().count();
        String members = membersCount == 0L ? "--" : membersStream.map(Employee::getName).collect(Collectors.joining(", "));

        writeTextAt(
                content,
                34.0f,
                760.0f,
                membersCount > 1L ? "Члены бригады: " + members
                        : "Член бригады: " + members,
                font,
                10.0f
        );
    }

    @SneakyThrows
    private void writeListContent(PDPageContentStream content, RequestSet worksheet, PDFont font) {
        final var requests = worksheet.getRequests().stream().limit(20).collect(Collectors.toList());

        for (int i = 0; i < requests.size(); i++) {
            writeSingleListLine(
                    content,
                    requests.get(i),
                    i * 33.2f,
                    i + 1,
                    font
            );
        }
    }

    private void writeSingleListLine(
            PDPageContentStream content,
            Request request,
            float yOffset,
            int position,
            PDFont font
    ) throws IOException {
        var r = new PrintableRequest(request);


        writeTextAt(content, position < 10 ? 58.0f : 55.0f, 723.0f - yOffset, String.valueOf(position), font, 10.0f);
        writeTextAt(content, 76.0f, 730.0f - yOffset, r.getAccountId(), font, 10.0f);
        writeTextAt(content, 120.0f, 730.0f - yOffset, StringUtils.substring(r.getName(), 0, 31), font, 9.0f);
        writeTextAt(content, 280.0f, 730.0f - yOffset, r.getName(), font, 9.0f);
        writeTextAt(content, 480.0f, 731.0f - yOffset, r.getReqType(), font, 10.0f);
        writeTextAt(content, 76.0f, 714.0f - yOffset, StringUtils.substring(r.getCounterInfo(), 0, 36), font, 10.0f);
        writeTextAt(content, 280.0f, 714.0f - yOffset, StringUtils.substring(r.getAdditions(), 0, 56), font, 9.0f);
    }

    private void writeMultiline(PDPageContentStream content,
                                float baseX,
                                float baseY,
                                float newLineX,
                                float newLineY,
                                int lineWidth,
                                String text,
                                PDFont font) throws IOException {
        writeMultiline(content, baseX, baseY, newLineX, newLineY, lineWidth, text, font, 12f, 14f);
    }

    private void writeMultiline(PDPageContentStream content,
                                float baseX,
                                float baseY,
                                float newLineX,
                                float newLineY,
                                int lineWidth,
                                String text,
                                PDFont font,
                                float fontSize,
                                float lineSpacing) throws IOException {
        String[] wrapped = WordUtils.wrap(text, lineWidth).split("\n");

        for (int i = 0; i < wrapped.length; i++) {
            if (i == 0)
                writeTextAt(content, baseX, baseY, wrapped[i], font, fontSize);
            else
                writeTextAt(content, newLineX, newLineY - (i - 1) * lineSpacing, wrapped[i], font, fontSize);
        }

    }

    private void writeTextAt(PDPageContentStream content,
                             float x,
                             float y,
                             String text,
                             PDFont font) throws IOException {
        writeTextAt(content, x, y, text, font, 12.0f);
    }

    private void writeTextAt(PDPageContentStream content,
                             float x,
                             float y,
                             String text,
                             PDFont font,
                             float fontSize) throws IOException {
        content.beginText();
        content.setNonStrokingColor(Color.BLACK);
        content.setFont(font, fontSize);
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
    }
}