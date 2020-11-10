package ru.alexandershirokikh.nrgorequestserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.dao.DistrictRepository;
import ru.alexandershirokikh.nrgorequestserver.entities.District;

import java.util.List;

/**
 * Controller for managing districts
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/districts")
public class DistrictController {
    private final DistrictRepository districtRepository;

    /**
     * Returns list of all districts
     */
    @GetMapping
    List<District> getAll() {
        return districtRepository.findAll();
    }

    /**
     * Adds a new district to the database
     */
    @PostMapping
    ResponseEntity<District> addNewDistrict(@RequestBody District district) {
        try {
            return ResponseEntity.ok(districtRepository.save(district));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes district with given id
     */
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        try {
            districtRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
