package ru.alexandershirokikh.nrgorequestserver.data.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CounterTypeDTO;

/**
 * Repository for accessing CounterType objects
 *
 * @see CounterTypeDTO
 */
public interface CounterTypeRepository extends JpaRepository<CounterTypeDTO, Integer> {
}
