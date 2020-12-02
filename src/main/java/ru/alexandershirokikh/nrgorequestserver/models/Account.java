package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Describes API request for adding or updating account info's
 */
@Data
public class Account {
    /**
     * Account info ID (account number)
     */
    @NotNull
    private final Integer baseId;

    /**
     * Account owner name
     */
    @Size(min = 2, max = 80)
    private final String name;

    /**
     * User address: street name
     */
    @NotNull
    private final Street street;

    /**
     * User address: home number
     */
    @NotEmpty
    @Size(max = 6)
    private final String homeNumber;

    /**
     * User address: apartment number
     */
    @Size(max = 6)
    private final String apartmentNumber;

    /**
     * User phone number
     */
    @Size(max = 12)
    private final String phoneNumber;

    /**
     * Combines all address parts to string
     */
    public String getFullAddress() {
        StringBuilder builder = new StringBuilder();

        builder.append(street.getName());
        builder.append(" д.");
        builder.append(homeNumber);

        if (apartmentNumber != null && !apartmentNumber.isBlank()) {
            builder.append(" кв.");
            builder.append(apartmentNumber);
        }
        return builder.toString();
    }
}
