package ru.alexandershirokikh.nrgorequestserver.core;

import ru.alexandershirokikh.nrgorequestserver.models.RequestSet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface IExporterService {

    /**
     * Creates Document and stores it to outputStream.
     *
     * @throws IOException on any creation error
     */
    void export(OutputStream outputStream) throws IOException;

    enum ExportFormat {
        PDF, XLSX
    }

    class ExporterServiceBuilder {
        private final List<RequestSet> requestSetList;

        public ExporterServiceBuilder(List<RequestSet> requestSetList) {
            this.requestSetList = requestSetList;
        }

        public IExporterService build(ExportFormat format) {
            switch (format) {
                case PDF:
                    return new PdfExporter(requestSetList);
                case XLSX:
                    return new XlsxExporter(requestSetList);
            }

            throw new IllegalArgumentException(format.name());
        }
    }
}
