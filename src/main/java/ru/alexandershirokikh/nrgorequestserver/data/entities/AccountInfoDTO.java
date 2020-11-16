package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Describes customer account info
 */
@Data
@NoArgsConstructor
@IdClass(AccountInfoKey.class)
@Entity(name = "account_info")
public class AccountInfoDTO {
    /**
     * Account info ID (account number)
     */
    @Id
    private Integer baseId;

    /**
     * Account info revision
     */
    @Id
    private Integer revision;

    /**
     * Account owner name
     */
    @Column(length = 80, nullable = false)
    private String name;

    /**
     * User address: street name
     */
    @ManyToOne
    @JoinColumn(name = "street_id", nullable = false)
    private StreetDTO street;

    /**
     * User address: home number
     */
    @Column(length = 6, nullable = false)
    private String homeNumber;

    /**
     * User address: apartment number
     */
    @Column(length = 6)
    private String apartmentNumber;

    /**
     * User phone number
     */
    @Column(length = 12)
    private String phoneNumber;
}
