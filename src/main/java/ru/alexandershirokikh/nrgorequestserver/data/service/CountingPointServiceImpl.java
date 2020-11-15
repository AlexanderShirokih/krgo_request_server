package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.data.dao.AccountInfoToCountingPointRepository;
import ru.alexandershirokikh.nrgorequestserver.data.dao.CountingPointsRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.*;
import ru.alexandershirokikh.nrgorequestserver.models.CountingPoint;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides counting point management operations
 */
@Service
@RequiredArgsConstructor
public class CountingPointServiceImpl implements CountingPointService {

    private final AccountInfoToCountingPointRepository repository;
    private final CountingPointsRepository countingPointsRepository;

    @Override
    public List<CountingPointDTO> getCountingPointsHistory(Integer baseId) {
        return repository.findByAccountInfoBaseId(baseId)
                .stream().map(AccountInfoToCountingPointDTO::getCountingPoint)
                .collect(Collectors.toList());
    }

    @Override
    public List<CountingPointDTO> getCountingPoints(String number) {
        return countingPointsRepository.findByCounterNumber(number);
    }

    @Override
    public AccountInfoToCountingPointDTO updateCountingPoint(AccountInfoDTO owner, CountingPoint countingPoint) {
        var counterType = new CounterTypeDTO();
        counterType.setId(countingPoint.getCounterTypeId());

        var existingCountingPoint = countingPointsRepository
                .findById(new CountingPointKey(countingPoint.getCounterNumber(), counterType.getId()));

        var newCountingPoint = createDTO(countingPoint);

        if (existingCountingPoint.isPresent()) {
            var existing = updateCountingPointIfNeeded(existingCountingPoint.get(), newCountingPoint);
            return updateAssignee(owner, existing);
        } else {
            var newCountingPointDTO = countingPointsRepository.save(newCountingPoint);
            return makeAssignee(owner, newCountingPointDTO);
        }
    }

    private AccountInfoToCountingPointDTO makeAssignee(AccountInfoDTO owner, CountingPointDTO countingPoint) {
        var accountToCountingPoint = new AccountInfoToCountingPointDTO();
        accountToCountingPoint.setAccountInfo(owner);
        accountToCountingPoint.setCountingPoint(countingPoint);
        return repository.save(accountToCountingPoint);
    }

    private AccountInfoToCountingPointDTO updateAssignee(AccountInfoDTO owner, CountingPointDTO existing) {
        return repository
                .findByAccountInfoAndCountingPoint(owner, existing)
                .orElseGet(() -> makeAssignee(owner, existing));
    }

    private CountingPointDTO updateCountingPointIfNeeded(CountingPointDTO currentDTO, CountingPointDTO newDTO) {
        if (!currentDTO.equals(newDTO)) {
            currentDTO.setTpName(newDTO.getTpName());
            currentDTO.setPillarNumber(newDTO.getPillarNumber());
            currentDTO.setFeederNumber(newDTO.getFeederNumber());
            currentDTO.setCounterTypeId(newDTO.getCounterTypeId());
            currentDTO.setCheckQuarter(newDTO.getCheckQuarter());
            currentDTO.setCheckYear(newDTO.getCheckYear());
            currentDTO.setPower(newDTO.getPower());
            return countingPointsRepository.save(currentDTO);
        }
        return currentDTO;
    }

    private CountingPointDTO createDTO(CountingPoint countingPoint) {
        var dto = new CountingPointDTO();

        dto.setCounterNumber(countingPoint.getCounterNumber());
        dto.setCheckQuarter(countingPoint.getCheckQuarter());
        dto.setCheckYear(countingPoint.getCheckYear());
        dto.setFeederNumber(countingPoint.getFeederNumber());
        dto.setPillarNumber(countingPoint.getPillarNumber());
        dto.setTpName(countingPoint.getTpName());

        if (countingPoint.getCounterTypeId() != null) {
            dto.setCounterTypeId(new CounterTypeDTO(countingPoint.getCounterTypeId(), null, null, null, null).getId());
        }

        return dto;
    }

}
