package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.data.dao.RequestRepository;
import ru.alexandershirokikh.nrgorequestserver.data.dao.RequestSetRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestSetDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.RequestTypeDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Request;
import ru.alexandershirokikh.nrgorequestserver.models.RequestSet;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides requests management operations
 */
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestSetRepository requestSetRepository;
    private final RequestRepository requestRepository;
    private final AccountInfoService accountService;
    private final CountingPointService countingPointService;

    @Override
    public void addRequest(Long setId, Request inputRequest) {
        var set = requestSetRepository.findById(setId);
        if (set.isPresent()) {
            var setDTO = set.get();
            var requestDTO = new RequestDTO();
            requestDTO.setRequestSet(setDTO);

            updateRequest(requestDTO, inputRequest);
            setDTO.getRequests().add(requestDTO);
        }
    }

    @Override
    public void updateRequest(Long requestId, Request inputRequest) {
        requestRepository.findById(requestId)
                .ifPresent(requestDTO -> updateRequest(requestDTO, inputRequest));
    }

    @Override
    public List<RequestDTO> getAll() {
        return requestRepository.findAll();
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public List<RequestSet> getAllRequestSets(Date date) {
        if (date == null)
            return requestSetRepository.findAll().stream().map(this::buildRequestSet).collect(Collectors.toList());
        else
            return requestSetRepository.findAllByDate(date).stream().map(this::buildRequestSet).collect(Collectors.toList());
    }

    private RequestSet buildRequestSet(RequestSetDTO dto) {
        return new RequestSet(dto.getId(), dto.getName(), dto.getDate());
    }

    @Override
    public List<RequestDTO> getAllRequestBySetId(Long id) {
        return requestSetRepository.findById(id)
                .map(RequestSetDTO::getRequests)
                .orElse(List.of());
    }

    @Override
    public void updateRequestSet(RequestSet updatedRequestSet) {
        RequestSetDTO dto = updatedRequestSet.getId() == null ? new RequestSetDTO() :
                requestSetRepository.findById(updatedRequestSet.getId()).orElse(new RequestSetDTO());

        dto.setId(updatedRequestSet.getId());
        dto.setName(updatedRequestSet.getName());
        dto.setDate(updatedRequestSet.getDate());
        requestSetRepository.save(dto);
    }

    @Transactional
    @Override
    public void moveRequest(Long targetId, List<Long> requests) {

        //@Query("select t from SixxxoView t where t.idbien IN (:idbienes) ")
        // public List<SixxxoView> findByIdbienes(
        //         @Param("idbienes") List<Long> idbienes);

        requestSetRepository
                .findById(targetId)
                .ifPresent(requestSetDTO -> requestRepository.findAllById(requests)
                        .forEach(requestDTO -> requestDTO.setRequestSet(requestSetDTO)));
    }

    private void updateRequest(RequestDTO requestDTO, Request inputRequest) {
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

        requestRepository.save(requestDTO);
    }

}
