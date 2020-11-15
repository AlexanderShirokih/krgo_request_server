package ru.alexandershirokikh.nrgorequestserver.data.service;

import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Request;
import ru.alexandershirokikh.nrgorequestserver.models.RequestSet;

import java.util.Date;
import java.util.List;

/**
 * Provides requests management operations
 */
public interface RequestService {

    /**
     * Updates existing request
     */
    void updateRequest(Long requestId, Request inputRequest);

    /**
     * Adds new request to existing set
     */
    void addRequest(Long setId, Request inputRequest);

    /**
     * Returns list containing all requests
     */
    List<RequestDTO> getAll();

    /**
     * Deletes request with {@code id }
     */
    void deleteRequest(Long id);

    /**
     * Returns list of all request sets
     *
     * @param date if present then only sets by this date will shown
     */
    List<RequestSet> getAllRequestSets(Date date);

    /**
     * Returns set of all requests by set id
     */
    List<RequestDTO> getAllRequestBySetId(Long id);

    /**
     * Adds new request set or updates existing if request set ID if not {@literal null}
     */
    void updateRequestSet(RequestSet newRequestSet);

    /**
     * Moves requests to another request set
     */
    void moveRequest(Long targetId, List<Long> requests);

}
