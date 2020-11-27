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
public class StreetController extends CRUDController<Street, StreetDTO, StreetRepository> {

    public StreetController(StreetRepository repository) {
        super(repository);
    }

    @Override
    protected StreetDTO createEntity(Street request) {
        var street = new StreetDTO();
        street.setId(request.getId());
        street.setName(request.getName());
        if (request.getDistrict() != null && request.getDistrict().getId() != null) {
            var district = new DistrictDTO();
            district.setId(request.getDistrict().getId());
            street.setDistrict(district);
        }
        return street;
    }
}
