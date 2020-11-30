package ru.alexandershirokikh.nrgorequestserver.data.service;

import org.springframework.data.domain.Page;
import ru.alexandershirokikh.nrgorequestserver.models.EmployeeAssignmentType;
import ru.alexandershirokikh.nrgorequestserver.models.Request;
import ru.alexandershirokikh.nrgorequestserver.models.RequestSet;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    Request addRequest(Long setId, Request inputRequest);

    /**
     * Returns list containing all requests
     */
    List<Request> getAll();

    /**
     * Deletes request with {@code id }
     */
    void deleteRequest(Long id);

    /**
     * Returns list of all request sets
     */
    Page<RequestSet> getAllRequestSets(Integer page, Integer size);

    /**
     * Gets list of all requests
     */
    List<RequestSet> getAllRequests(Date date, Boolean full);

    /**
     * Returns set of all requests by set id
     */
    Optional<RequestSet> getAllRequestBySetId(Long id);

    /**
     * Adds new request set or updates existing if request set ID if not {@literal null}
     */
    RequestSet updateRequestSet(RequestSet newRequestSet);

    /**
     * Deletes request set by its ID
     */
    void deleteRequestSet(Long worksheet);

    /**
     * Moves requests to another request set
     */
    void moveRequest(Long targetId, List<Long> requests);

    /**
     * Assigns employee to request set
     */
    void assignEmployee(Long requestSetId, Integer employeeId, EmployeeAssignmentType assignmentType);

    /**
     * Detaches employee from request set
     */
    void detachEmployee(Long requestSetId, Integer employeeId);

}
