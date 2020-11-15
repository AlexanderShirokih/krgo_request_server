package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestDTO;
import ru.alexandershirokikh.nrgorequestserver.data.service.RequestService;
import ru.alexandershirokikh.nrgorequestserver.models.Request;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    /**
     * Adds a new entity to the database from given request
     */
    @PostMapping({"/", "/{id}"})
    public RequestDTO updateRequest(@PathVariable(required = false, name = "id") @Positive Long id, @Valid @RequestBody Request inputRequest) {
        return requestService.processRequest(id, inputRequest);
    }

    /**
     * Deletes the request with {@literal id}
     */
    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable(name = "id") @Positive Long id) {
        requestService.deleteRequest(id);
    }

    // TODO: Temp
    @GetMapping
    public List<RequestDTO> getAllRequests() {
        return requestService.getAll();
    }
}
