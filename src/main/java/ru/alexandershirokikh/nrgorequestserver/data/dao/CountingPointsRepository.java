package ru.alexandershirokikh.nrgorequestserver.data.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CountingPointDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CountingPointPK;

import java.util.List;

/**
 * Repository for accessing CounterType objects
 *
 * @see CountingPointDTO
 */
public interface CountingPointsRepository extends JpaRepository<CountingPointDTO, CountingPointPK> {

    /**
     * Finds all counters by it's number
     */
    List<CountingPointDTO> findByKeyCounterNumber(String counterNumber);

}

