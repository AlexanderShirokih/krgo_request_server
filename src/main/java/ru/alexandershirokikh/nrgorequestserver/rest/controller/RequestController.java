package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestDTO;
import ru.alexandershirokikh.nrgorequestserver.data.service.RequestService;
import ru.alexandershirokikh.nrgorequestserver.models.Request;
import ru.alexandershirokikh.nrgorequestserver.models.RequestSet;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

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
    public List<RequestSet> getAllRequestSets(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return requestService.getAllRequestSets(date);
    }

    /**
     * Gets all requests by set id
     */
    @GetMapping("/{id}")
    public List<RequestDTO> getAllRequestBySetId(@PathVariable("id") @Positive Long id) {
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
    @PostMapping("/{id}/move")
    public void moveRequest(@PathVariable("id") @Positive Long targetId,
                            @RequestParam("ids[]") List<Long> requests) {
        requestService.moveRequest(targetId, requests);
    }

}
