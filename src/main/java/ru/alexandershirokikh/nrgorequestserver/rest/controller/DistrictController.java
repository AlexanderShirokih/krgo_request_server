package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandershirokikh.nrgorequestserver.data.dao.DistrictRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.DistrictDTO;
import ru.alexandershirokikh.nrgorequestserver.models.District;

/**
 * Controller for managing districts
 */
@RestController
@RequestMapping("/districts")
public class DistrictController extends CRDController<District, DistrictDTO, DistrictRepository> {

    public DistrictController(DistrictRepository repository) {
        super(repository);
    }

    @Override
    protected DistrictDTO createEntity(District request) {
        var district = new DistrictDTO();
        district.setName(request.getName());
        return district;
    }
}
