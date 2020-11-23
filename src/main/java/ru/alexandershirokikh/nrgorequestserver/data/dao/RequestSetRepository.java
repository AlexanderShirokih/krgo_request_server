package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestSetDTO;

import java.util.Date;
import java.util.List;

/**
 * Manages request sets
 */
public interface RequestSetRepository extends JpaRepository<RequestSetDTO, Long> {
    /**
     * Finds all request dates
     */
    @Query("SELECT DISTINCT date FROM request_set")
    List<Date> findAllDistinctDates();

    /**
     * Finds all request sets dated by {@code year} and {@code month}
     */
    @Query("SELECT r FROM request_set r WHERE year(r.date) = :year AND month(r.date) = :month")
    List<RequestSetDTO> findAllByDate(@Param("year") Integer year, @Param("month") Integer month);
}
