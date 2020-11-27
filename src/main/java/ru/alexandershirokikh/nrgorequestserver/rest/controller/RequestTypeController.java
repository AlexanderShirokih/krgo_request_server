package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandershirokikh.nrgorequestserver.data.dao.RequestTypeRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestTypeDTO;
import ru.alexandershirokikh.nrgorequestserver.models.RequestType;

/**
 * Controller for managing request types
 */
@RequestMapping("/requests/types")
@RestController
public class RequestTypeController extends CRUDController<RequestType, RequestTypeDTO, RequestTypeRepository> {

    public RequestTypeController(RequestTypeRepository repository) {
        super(repository);
    }

    @Override
    protected RequestTypeDTO createEntity(RequestType request) {
        var requestType = new RequestTypeDTO();
        requestType.setId(request.getId());
        requestType.setShortName(request.getShortName());
        requestType.setFullName(request.getFullName());
        return requestType;
    }
}
