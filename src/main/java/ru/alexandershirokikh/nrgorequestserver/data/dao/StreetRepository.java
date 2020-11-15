package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.StreetDTO;

/**
 * Repository for accessing Street objects
 *
 * @see StreetDTO
 */
public interface StreetRepository extends JpaRepository<StreetDTO, Integer> {
}
