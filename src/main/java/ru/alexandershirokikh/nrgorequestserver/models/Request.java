package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Describes customer request
 */
@Data
public class Request {
    /**
     * Request ID. Used for response
     */
    private final Long id;

    /**
     * Comments to the request
     */
    @NotNull
    @Size(max = 80)
    private final String additional;

    /**
     * Request initiation reason
     */
    @NotNull
    @Size(max = 64)
    private final String reason;

    /**
     * Associated request type
     */
    @Positive
    @NotNull
    private final Integer requestTypeId;

    /**
     * Owning account info
     */
    private final Account accountInfo;

    /**
     * Referenced counting point
     */
    private final CountingPoint countingPoint;

}
