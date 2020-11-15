package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.data.dao.RequestRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestTypeDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Request;

import java.util.List;

/**
 * Provides requests management operations
 */
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final AccountInfoService accountService;
    private final CountingPointService countingPointService;

    @Override
    public RequestDTO processRequest(Long requestId, Request inputRequest) {
        if (requestId != null) {
            // Update an existing request
            var existingRequest = requestRepository.findById(requestId);

            if (existingRequest.isPresent()) {
                return updateRequest(existingRequest.get(), inputRequest);
            }
        }

        // Persist new request
        return updateRequest(new RequestDTO(), inputRequest);
    }

    @Override
    public List<RequestDTO> getAll() {
        return requestRepository.findAll();
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    private RequestDTO updateRequest(RequestDTO requestDTO, Request inputRequest) {
        requestDTO.setAdditional(inputRequest.getAdditional());
        requestDTO.setReason(inputRequest.getReason());

        var requestTypeDTO = new RequestTypeDTO();
        requestTypeDTO.setId(inputRequest.getRequestTypeId());
        requestDTO.setRequestType(requestTypeDTO);

        var accountInfo = inputRequest.getAccountInfo();
        if (accountInfo != null) {
            // Update account info
            var accountDTO = accountService.updateAccount(accountInfo);
            requestDTO.setAccountInfo(accountDTO);

            var countingPoint = inputRequest.getCountingPoint();
            if (countingPoint != null) {
                // Update counting point
                var countingPointDTO = countingPointService.updateCountingPoint(accountDTO, countingPoint);

                requestDTO.setCountingPoint(countingPointDTO);
            } else {
                requestDTO.setCountingPoint(null);
            }
        } else {
            requestDTO.setCountingPoint(null);
            requestDTO.setAccountInfo(null);
        }

        return requestRepository.save(requestDTO);
    }

}
