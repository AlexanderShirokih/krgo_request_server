package ru.alexandershirokikh.nrgorequestserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.api.AddStreetRequest;
import ru.alexandershirokikh.nrgorequestserver.dao.StreetRepository;
import ru.alexandershirokikh.nrgorequestserver.entities.District;
import ru.alexandershirokikh.nrgorequestserver.entities.Street;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Controller for managing streets
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/streets")
public class StreetController {
    private final StreetRepository streetRepository;

    /**
     * Returns list of all streets
     */
    @GetMapping
    List<Street> getAll() {
        return streetRepository.findAll();
    }

    /**
     * Adds a new district to the database
     */
    @PostMapping
    ResponseEntity<Street> addNewStreet(@Valid @RequestBody AddStreetRequest newStreet) {
        try {
            var street = new Street();
            street.setName(newStreet.getName());
            if (newStreet.getDistrictId() != null) {
                var district = new District();
                district.setId(newStreet.getDistrictId());
                street.setDistrict(district);
            }

            return ResponseEntity.ok(streetRepository.save(street));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes street with given id
     */
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteStreet(@PathVariable @Min(1) Integer id) {
        try {
            streetRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
