package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.models.Account;
import ru.alexandershirokikh.nrgorequestserver.data.dao.AccountInfoRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.StreetDTO;

import java.util.List;
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

            final var currentAccountStreet = currentAccount.getStreet();
            if (currentAccountStreet != null) {
                newAccountInfo.getStreet().setDistrict(currentAccountStreet.getDistrict());
                newAccountInfo.getStreet().setName(currentAccountStreet.getName());
            }

            if (!currentAccount.equals(newAccountInfo)) {
                newAccountInfo.setRevision(currentAccountRevision + 1);
                return accountInfoRepository.save(newAccountInfo);
            }

            return currentAccount;
        } else {
            // We don't have account with this base is yet
            return accountInfoRepository.save(newAccountInfo);
        }
    }

    private AccountInfoDTO createNewAccountInfo(Account updateRequest) {
        var accountInfo = new AccountInfoDTO();
        accountInfo.setBaseId(updateRequest.getBaseId());
        accountInfo.setRevision(1);
        accountInfo.setName(updateRequest.getName());
        accountInfo.setHomeNumber(updateRequest.getHomeNumber());
        accountInfo.setApartmentNumber(updateRequest.getApartmentNumber());
        accountInfo.setPhoneNumber(updateRequest.getPhoneNumber());

        if (updateRequest.getStreet() != null) {
            accountInfo.setStreet(new StreetDTO(updateRequest.getStreet().getId(), null, null));
        }
        return accountInfo;
    }

}
