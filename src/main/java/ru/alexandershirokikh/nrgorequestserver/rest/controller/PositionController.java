package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandershirokikh.nrgorequestserver.data.dao.PositionRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.PositionDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Position;

@RestController
@RequestMapping("/positions")
public class PositionController extends CRUDController<Position, PositionDTO, PositionRepository> {

    public PositionController(PositionRepository repository) {
        super(repository);
    }

    @Override
    protected PositionDTO createEntity(Position request) {
        var dto = new PositionDTO();
        dto.setId(request.getId());
        dto.setName(request.getName());
        return dto;
    }
}
