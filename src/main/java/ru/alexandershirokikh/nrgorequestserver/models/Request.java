package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Describes customer request
 */
@Data
@NoArgsConstructor
public class Request {

    /**
     * Comments to the request
     */
    @NotNull
    @Size(max = 80)
    private String additional;

    /**
     * Request initiation reason
     */
    @NotNull
    @Size(max = 64)
    private String reason;

    /**
     * Associated request type
     */
    @Positive
    @NotNull
    private Integer requestTypeId;

    /**
     * Owning account info
     */
    private Account accountInfo;

    /**
     * Referenced counting point
     */
    private CountingPoint countingPoint;

}
