package ru.alexandershirokikh.nrgorequestserver.data.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CountingPointDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CountingPointKey;

import java.util.List;

/**
 * Repository for accessing CounterType objects
 *
 * @see CountingPointDTO
 */
public interface CountingPointsRepository extends JpaRepository<CountingPointDTO, CountingPointKey> {

    /**
     * Finds all counters by it's number
     */
    List<CountingPointDTO> findByCounterNumber(String counterNumber);

}

