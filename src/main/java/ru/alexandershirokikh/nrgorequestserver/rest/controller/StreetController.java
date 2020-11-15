package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandershirokikh.nrgorequestserver.data.dao.StreetRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.DistrictDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.StreetDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Street;

/**
 * Controller for managing streets
 */
@RestController
@RequestMapping("/streets")
public class StreetController extends CRDController<Street, StreetDTO, StreetRepository> {

    public StreetController(StreetRepository repository) {
        super(repository);
    }

    @Override
    protected StreetDTO createEntity(Street request) {
        var street = new StreetDTO();
        street.setName(request.getName());
        if (request.getDistrictId() != null) {
            var district = new DistrictDTO();
            district.setId(request.getDistrictId());
            street.setDistrict(district);
        }
        return street;
    }
}
