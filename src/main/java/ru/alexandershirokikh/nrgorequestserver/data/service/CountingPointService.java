package ru.alexandershirokikh.nrgorequestserver.data.service;

import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.AccountInfoToCountingPointDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.CountingPointDTO;
import ru.alexandershirokikh.nrgorequestserver.models.CountingPoint;

import java.util.List;

/**
 * Provides counting point management operations
 */
public interface CountingPointService {

    /**
     * Finds all counting points that was assigned to {@literal baseId}
     */
    List<CountingPointDTO> getCountingPointsHistory(Integer baseId);

    /**
     * Finds all counting points by counter number
     */
    List<CountingPointDTO> getCountingPoints(String number);

    /**
     * Creates new counting point if it doesn't created yet or updates it if it was changed.
     * Also attaches the {@code countingPoint} to it to existing {@code owner}
     */
    AccountInfoToCountingPointDTO updateCountingPoint(AccountInfoDTO owner, CountingPoint countingPoint);
}
