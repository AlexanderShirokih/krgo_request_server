package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.DistrictDTO;

/**
 * Repository for accessing DistrictDTO objects
 *
 * @see DistrictDTO
 */
public interface DistrictRepository extends JpaRepository<DistrictDTO, Integer> {
}
