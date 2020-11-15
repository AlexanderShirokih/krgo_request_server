package ru.alexandershirokikh.nrgorequestserver.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * An entity that map account info to counting point
 */
@Data
@NoArgsConstructor
@Entity(name = "account_info_to_counter_point")
@JsonIgnoreProperties({"accountInfo", "id"})
public class AccountInfoToCountingPointDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Associated account
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "account_id", referencedColumnName = "baseId"),
            @JoinColumn(name = "account_rev", referencedColumnName = "revision"),
    })
    private AccountInfoDTO accountInfo;

    /**
     * References counting point
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "counter_type_id", referencedColumnName = "counter_type_id"),
            @JoinColumn(name = "counting_point_number", referencedColumnName = "counterNumber"),
    })
    private CountingPointDTO countingPoint;
}
