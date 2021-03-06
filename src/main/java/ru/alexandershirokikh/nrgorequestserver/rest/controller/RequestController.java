package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.data.service.RequestService;
import ru.alexandershirokikh.nrgorequestserver.models.EmployeeAssignmentType;
import ru.alexandershirokikh.nrgorequestserver.models.Request;
import ru.alexandershirokikh.nrgorequestserver.models.RequestSet;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller that manages requests and request sets
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    /**
     * Adds a new entity to the database from given request
     */
    @PutMapping("/{id}")
    public Request addRequest(@PathVariable(name = "id") @Positive Long setId, @Valid @RequestBody Request inputRequest) {
        return requestService.addRequest(setId, inputRequest);
    }

    /**
     * Updates an existing request
     */
    @PostMapping("/{id}")
    public void updateRequest(@PathVariable(name = "id") @Positive Long requestId, @Valid @RequestBody Request inputRequest) {
        requestService.updateRequest(requestId, inputRequest);
    }

    /**
     * Deletes the request with {@literal id}
     */
    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable(name = "id") @Positive Long id) {
        requestService.deleteRequest(id);
    }

    /**
     * Gets list of all request sets
     */
    @GetMapping
    public Page<RequestSet> getAllRequestSets(@RequestParam(value = "page", required = false, defaultValue = "0") @PositiveOrZero Integer page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size) {
        return requestService.getAllRequestSets(page, size);
    }

    /**
     * Gets list of all requests optionally filtering by date.
     */
    @GetMapping("/all")
    public List<RequestSet> getAllRequests(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam(value = "full", required = false, defaultValue = "false") Boolean isFull
    ) {
        return requestService.getAllRequests(date, isFull);
    }

    /**
     * Gets all requests by set id
     */
    @GetMapping("/{id}")
    public Optional<RequestSet> getAllRequestBySetId(@PathVariable("id") @Positive Long id) {
        return requestService.getAllRequestBySetId(id);
    }

    /**
     * Adds new empty request set or updates it if ID is {@literal null}
     */
    @PostMapping("/worksheets/")
    public RequestSet updateRequestSet(@Valid @RequestBody RequestSet newRequestSet) {
        return requestService.updateRequestSet(newRequestSet);
    }

    /**
     * Deletes request set and all its requests by request set ID
     */
    @DeleteMapping("/worksheets/{id}")
    public void deleteRequestSet(@PathVariable("id") @Positive Long worksheetId) {
        requestService.deleteRequestSet(worksheetId);
    }

    /**
     * Reassigns request to another request set
     *
     * @param targetId target request set id
     * @param requests moving request
     */
    @PostMapping("{id}/move")
    public void moveRequest(@PathVariable("id") @Positive Long targetId,
                            @RequestParam("ids[]") List<Long> requests) {
        requestService.moveRequest(targetId, requests);
    }

    /**
     * Assigns employee to request set
     */
    @PostMapping("/{id}/employees/{eid}/{type}")
    public void attachEmployee(@PathVariable("id") @Positive Long requestSetId,
                               @PathVariable("eid") @Positive Integer employeeId,
                               @PathVariable("type") EmployeeAssignmentType assignmentType) {
        requestService.assignEmployee(requestSetId, employeeId, assignmentType);
    }

    /**
     * Detaches employee from request set
     */
    @DeleteMapping("/{id}/employees/{eid}")
    public void detachEmployee(
            @PathVariable("id") @Positive Long requestSetId,
            @PathVariable("eid") @Positive Integer employeeId
    ) {
        requestService.detachEmployee(requestSetId, employeeId);
    }
}
