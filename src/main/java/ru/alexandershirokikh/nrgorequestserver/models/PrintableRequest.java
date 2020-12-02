package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class PrintableRequest {

    private final String accountId;
    private final String name;
    private final String address;
    private final String reqType;
    private final String counterInfo;
    private final String additions;

    public PrintableRequest(Request request) {
        Account account = request.getAccountInfo();

        accountId = account == null || account.getBaseId() == null
                ? "--"
                : StringUtils.leftPad(String.valueOf(account.getBaseId()), 6, "0");

        name = account == null || account.getName() == null
                ? "--"
                : account.getName();

        address = account == null
                ? "--"
                : account.getFullAddress();

        reqType = request.getRequestType().getShortName();

        counterInfo = request.getCountingPoint() == null
                ? "ПУ отсутств."
                : request.getCountingPoint().getCounterInfo();

        String connectionPoint = request.getCountingPoint() == null
                ? ""
                : request.getCountingPoint().getConnectionPoint();

        String phone = account == null
                ? ""
                : account.getPhoneNumber();

        String additionalInfo = request.getAdditional() == null
                ? ""
                : request.getAdditional();

        additions = String.join(" |", connectionPoint, phone, additionalInfo);
    }
}
