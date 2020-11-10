package ru.alexandershirokikh.nrgorequestserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.entities.District;

/**
 * Repository for accessing District objects
 *
 * @see District
 */
public interface DistrictRepository extends JpaRepository<District, Integer> {
}
