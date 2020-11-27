package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandershirokikh.nrgorequestserver.data.dao.CounterTypeRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CounterTypeDTO;
import ru.alexandershirokikh.nrgorequestserver.models.CounterType;

/**
 * Controller for managing counter types
 */
@RestController
@RequestMapping("/counters")
public class CounterTypeController extends CRUDController<CounterType, CounterTypeDTO, CounterTypeRepository> {

    public CounterTypeController(CounterTypeRepository repository) {
        super(repository);
    }

    @Override
    protected CounterTypeDTO createEntity(CounterType request) {
        var counterType = new CounterTypeDTO();
        counterType.setId(request.getId());
        counterType.setName(request.getName());
        counterType.setAccuracy(request.getAccuracy());
        counterType.setBits(request.getBits());
        counterType.setSinglePhased(request.getSinglePhased());
        return counterType;
    }

}
