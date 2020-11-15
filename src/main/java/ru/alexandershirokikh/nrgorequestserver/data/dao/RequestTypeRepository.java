package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestTypeDTO;

/**
 * Repository for accessing {@code RequestType} objects
 *
 * @see RequestTypeDTO
 */
public interface RequestTypeRepository extends JpaRepository<RequestTypeDTO, Integer> {

}
