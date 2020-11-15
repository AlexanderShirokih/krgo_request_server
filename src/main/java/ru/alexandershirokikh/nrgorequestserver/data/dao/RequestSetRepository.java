package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestSetDTO;

import java.util.Date;
import java.util.List;

/**
 * Manages request sets
 */
public interface RequestSetRepository extends JpaRepository<RequestSetDTO, Long> {
    /**
     * Finds all request sets by given date
     */
    List<RequestSetDTO> findAllByDate(Date date);
}
