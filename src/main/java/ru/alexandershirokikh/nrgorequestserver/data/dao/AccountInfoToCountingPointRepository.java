package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoToCountingPointDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CountingPointDTO;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing {@code AccountInfoToCountingPoint} objects
 *
 * @see AccountInfoToCountingPointDTO
 */
public interface AccountInfoToCountingPointRepository extends JpaRepository<AccountInfoToCountingPointDTO, Long> {

    /**
     * Finds all counting points by it references account id
     */
    List<AccountInfoToCountingPointDTO> findByAccountInfoBaseId(Integer accountId);

    /**
     * Finds association between account info and counting point
     */
    Optional<AccountInfoToCountingPointDTO> findByAccountInfoAndCountingPoint(AccountInfoDTO a, CountingPointDTO c);

}