package ru.alexandershirokikh.nrgorequestserver.data.service;

import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Request;

import java.util.List;

/**
 * Provides requests management operations
 */
public interface RequestService {

    /**
     * Processes input request.
     * If request {@code id} is not {@literal null} new request entity will be created.
     *
     * @return processed request with actual {@code id}
     */
    RequestDTO processRequest(Long requestId, Request inputRequest);

    /**
     * Returns list containing all requests
     */
    List<RequestDTO> getAll();

    /**
     * Deletes request with {@code id }
     */
    void deleteRequest(Long id);
}
