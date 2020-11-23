package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.data.service.RequestService;
import ru.alexandershirokikh.nrgorequestserver.models.EmployeeAssignmentType;
import ru.alexandershirokikh.nrgorequestserver.models.Request;
import ru.alexandershirokikh.nrgorequestserver.models.RequestSet;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
    public void addRequest(@PathVariable(name = "id") @Positive Long setId, @Valid @RequestBody Request inputRequest) {
        requestService.addRequest(setId, inputRequest);
    }

    /**
     * Updates an existing request
     */
    @PostMapping("/{id}")
    public void updateRequest(@PathVariable(name = "id") @Positive Long id, @Valid @RequestBody Request inputRequest) {
        requestService.updateRequest(id, inputRequest);
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
     * Gets list of all dates where request sets exists in format yyyy-MM-dd
     */
    @GetMapping("/dates")
    public List<String> getAllRequestDates() {
        return requestService.getAllRequestDates();
    }

    /**
     * Gets list of all request sets dated by year and month
     */
    @GetMapping("/dates/{year}/{month}")
    public List<RequestSet> getAllRequestSetsByYearAndMonth(@PathVariable("year") @Positive Integer year,
                                                            @PathVariable("month") @Positive @Max(12) Integer month) {
        return requestService.getAllRequestSetsByYearAndMonth(year, month);
    }

    /**
     * Gets all requests by set id
     */
    @GetMapping("/{id}")
    public Optional<RequestSet> getAllRequestBySetId(@PathVariable("id") @Positive Long id) {
        return requestService.getAllRequestBySetId(id);
    }

    /**
     * Adds new empty request set or update it if ID is {@literal null}
     */
    @PostMapping
    public void updateRequestSet(@Valid @RequestBody RequestSet newRequestSet) {
        requestService.updateRequestSet(newRequestSet);
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
