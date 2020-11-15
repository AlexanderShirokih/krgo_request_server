package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.PositionDTO;

/**
 * Repository for managing {@code PositionDTO} instances
 */
public interface PositionRepository extends JpaRepository<PositionDTO, Integer> {
}
