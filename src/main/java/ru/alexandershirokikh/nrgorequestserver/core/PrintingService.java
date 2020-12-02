package ru.alexandershirokikh.nrgorequestserver.core;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PrintingService {

    /**
     * Returns a list of print services available on server
     */
    public List<String> getAvailablePrinters() {
        final HashPrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
        attrSet.add(Sides.TWO_SIDED_SHORT_EDGE);

        return Arrays.stream(PrintServiceLookup
                .lookupPrintServices(null, attrSet))
                .map(PrintService::getName)
                .collect(Collectors.toList());
    }

    /**
     * Finds {@link PrintService} by printerName
     */
    public Optional<PrintService> findPrintService(String printerName) {
        return Arrays.stream(PrintServiceLookup
                .lookupPrintServices(null, null))
                .filter(printService -> printService.getName().equals(printerName))
                .findFirst();
    }
}
