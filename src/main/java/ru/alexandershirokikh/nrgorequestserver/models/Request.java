package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @Size(max = 80)
    private final String additional;

    /**
     * Request initiation reason
     */
    @Size(max = 64)
    private final String reason;

    /**
     * Associated request type
     */
    @NotNull
    private final RequestType requestType;

    /**
     * Owning account info
     */
    @NotNull
    @Valid
    private final Account accountInfo;

    /**
     * Referenced counting point
     */
    private final CountingPoint countingPoint;

}
