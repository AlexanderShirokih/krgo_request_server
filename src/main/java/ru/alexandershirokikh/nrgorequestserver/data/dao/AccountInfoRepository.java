package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing {@code AccountInfo} objects
 *
 * @see AccountInfoDTO
 */
public interface AccountInfoRepository extends JpaRepository<AccountInfoDTO, Integer> {

    /**
     * Finds all accounts by it's base id
     */
    List<AccountInfoDTO> findByBaseId(Integer baseId);

    Optional<AccountInfoDTO> findFirstByBaseIdOrderByRevisionDesc(Integer baseId);

}
