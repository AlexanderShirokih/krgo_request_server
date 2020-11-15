package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Describes API request for adding or updating account info's
 */
@NoArgsConstructor
@Data
public class Account {
    /**
     * Account info ID (account number)
     */
    @NotNull
    private Integer baseId;

    /**
     * Account owner name
     */
    @Size(min = 2, max = 80)
    private String name;

    /**
     * User address: street name
     */
    @NotNull
    @Positive
    private Integer streetId;

    /**
     * User address: home number
     */
    @NotEmpty
    @Size(max = 6)
    private String homeNumber;

    /**
     * User address: apartment number
     */
    @Size(max = 6)
    private String apartmentNumber;
}
