package ru.alexandershirokikh.nrgorequestserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.entities.Street;

/**
 * Repository for accessing Street objects
 *
 * @see Street
 */
public interface StreetRepository extends JpaRepository<Street, Integer> {
}
