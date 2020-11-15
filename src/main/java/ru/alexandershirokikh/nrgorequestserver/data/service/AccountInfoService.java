package ru.alexandershirokikh.nrgorequestserver.data.service;

import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Account;

import java.util.List;
import java.util.Optional;

/**
 * Provides account management operations
 */
public interface AccountInfoService {

    /**
     * Returns all account change history for specified {@literal baseId}
     */
    List<AccountInfoDTO> getAccountHistory(Integer baseId);

    /**
     * Returns latest account info associated with this {@literal baseId}
     */
    Optional<AccountInfoDTO> getAccountInfo(Integer baseId);

    /**
     * Creates new account if it doesn't created yet or updates it if it was changed
     */
    AccountInfoDTO updateAccount(Account updateRequest);
}
