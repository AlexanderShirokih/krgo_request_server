package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.DistrictDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestDTO;

/**
 * Repository for accessing RequestDTO objects
 *
 * @see DistrictDTO
 */
public interface RequestRepository extends JpaRepository<RequestDTO, Long> {
}
