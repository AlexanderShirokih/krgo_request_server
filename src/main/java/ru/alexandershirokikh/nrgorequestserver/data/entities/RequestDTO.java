package ru.alexandershirokikh.nrgorequestserver.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Describes customer request
 */
@Data
@NoArgsConstructor
@Entity(name = "request")
@JsonIgnoreProperties("requestSet")
public class RequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Comments to the request
     */
    @Column(length = 80, nullable = false)
    private String additional;

    /**
     * Request initiation reason
     */
    @Column(length = 64, nullable = false)
    private String reason;

    /**
     * Associated request type
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private RequestTypeDTO requestType;

    /**
     * Owning account info
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "account_id", referencedColumnName = "baseId"),
            @JoinColumn(name = "account_rev", referencedColumnName = "revision")
    })
    private AccountInfoDTO accountInfo;

    /**
     * Referenced counting point
     */
    @ManyToOne
    @JoinColumn(name = "counting_point_reference_id")
    private AccountInfoToCountingPointDTO countingPointAssignment;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_set_id", nullable = false)
    public RequestSetDTO requestSet;

}
