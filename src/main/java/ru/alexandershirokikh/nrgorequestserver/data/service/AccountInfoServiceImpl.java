package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.data.dao.AccountInfoRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.StreetDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Account;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Provides account management operations
 */
@Service
@RequiredArgsConstructor
public class AccountInfoServiceImpl implements AccountInfoService {

    private final AccountInfoRepository accountInfoRepository;

    @Override
    public List<AccountInfoDTO> getAccountHistory(Integer baseId) {
        return accountInfoRepository.findByBaseId(baseId);
    }

    @Override
    public Optional<AccountInfoDTO> getAccountInfo(Integer baseId) {
        return accountInfoRepository
                .findFirstByBaseIdOrderByRevisionDesc(baseId);
    }

    @Override
    public AccountInfoDTO updateAccount(Account updateRequest) {
        final var newAccountInfo = createNewAccountInfo(updateRequest);

        final var currentAccountOpt = getAccountInfo(updateRequest.getBaseId());

        if (currentAccountOpt.isPresent()) {
            final var currentAccount = currentAccountOpt.get();

            // Copy these fields value to be able to correctly compare them
            final var currentAccountRevision = currentAccount.getRevision();
            newAccountInfo.setRevision(currentAccountRevision);

            if (!compareAccounts(currentAccount, newAccountInfo)) {
                newAccountInfo.setRevision(currentAccountRevision + 1);
                return accountInfoRepository.save(newAccountInfo);
            }

            return currentAccount;
        } else {
            // We don't have account with this base is yet
            return accountInfoRepository.save(newAccountInfo);
        }
    }

    private static boolean compareAccounts(AccountInfoDTO a, AccountInfoDTO b) {
        return
                Objects.equals(a.getRevision(), b.getRevision()) &&
                        Objects.equals(a.getBaseId(), b.getBaseId()) &&
                        Objects.equals(a.getApartmentNumber(), b.getApartmentNumber()) &&
                        Objects.equals(a.getHomeNumber(), b.getHomeNumber()) &&
                        Objects.equals(a.getName(), b.getName()) &&
                        Objects.equals(a.getPhoneNumber(), b.getPhoneNumber()) &&
                        Objects.equals(a.getStreet().getId(), b.getStreet().getId());
    }

    private AccountInfoDTO createNewAccountInfo(Account updateRequest) {
        var accountInfo = new AccountInfoDTO();
        accountInfo.setBaseId(updateRequest.getBaseId());
        accountInfo.setRevision(1);
        accountInfo.setName(updateRequest.getName());
        accountInfo.setHomeNumber(updateRequest.getHomeNumber());
        accountInfo.setApartmentNumber(updateRequest.getApartmentNumber());
        accountInfo.setPhoneNumber(updateRequest.getPhoneNumber());
        accountInfo.setStreet(new StreetDTO(updateRequest.getStreet().getId(), null, null));
        return accountInfo;
    }

}
