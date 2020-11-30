package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.data.dao.EmployeeAssignmentRepository;
import ru.alexandershirokikh.nrgorequestserver.data.dao.EmployeeRepository;
import ru.alexandershirokikh.nrgorequestserver.data.dao.RequestRepository;
import ru.alexandershirokikh.nrgorequestserver.data.dao.RequestSetRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.*;
import ru.alexandershirokikh.nrgorequestserver.models.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides requests management operations
 */
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final EmployeeAssignmentRepository employeeAssignmentRepository;
    private final RequestSetRepository requestSetRepository;
    private final RequestRepository requestRepository;
    private final EmployeeRepository employeeRepository;
    private final AccountInfoService accountService;
    private final CountingPointService countingPointService;

    @Override
    public Request addRequest(Long setId, Request inputRequest) {
        var set = requestSetRepository.findById(setId);
        if (set.isPresent()) {
            var setDTO = set.get();
            var requestDTO = new RequestDTO();
            requestDTO.setRequestSet(setDTO);

            setDTO.getRequests().add(requestDTO);
            return updateRequest(requestDTO, inputRequest);
        }
        return null;
    }

    @Override
    public void updateRequest(Long requestId, Request inputRequest) {
        requestRepository.findById(requestId)
                .ifPresent(requestDTO -> updateRequest(requestDTO, inputRequest));
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAll().stream()
                .map(this::buildRequest)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Page<RequestSet> getAllRequestSets(Integer pageNum,
                                              Integer size) {
        Pageable sortedByDateDesc =
                pageNum == null || size == null ? Pageable.unpaged() :
                        PageRequest.of(pageNum, size, Sort.by("date").descending());

        return requestSetRepository.findAll(sortedByDateDesc).map(this::buildShortRequestSet);
    }

    @Override
    public List<RequestSet> getAllRequests(Date date, Boolean full) {
        return (date == null ? requestSetRepository.findAll() : requestSetRepository.findAllByDate(date))
                .stream()
                .map(dto -> full ? buildRequestSet(dto) : buildShortRequestSet(dto))
                .collect(Collectors.toList());
    }

    private RequestSet buildShortRequestSet(RequestSetDTO dto) {
        return new RequestSet(dto.getId(), dto.getName(), dto.getDate(), null, null);
    }

    @Override
    public Optional<RequestSet> getAllRequestBySetId(Long id) {
        return requestSetRepository.findById(id).map(this::buildRequestSet);
    }

    private RequestSet buildRequestSet(RequestSetDTO requestSetDTO) {
        return new RequestSet(
                requestSetDTO.getId(),
                requestSetDTO.getName(),
                requestSetDTO.getDate(),
                requestSetDTO.getRequests().stream().map(this::buildRequest)
                        .collect(Collectors.toList()),
                requestSetDTO.getAssignments().stream().map(this::buildAssignment)
                        .collect(Collectors.toList())
        );
    }

    private EmployeeAssignment buildAssignment(EmployeeAssignmentDTO assignmentDTO) {
        return new EmployeeAssignment(
                buildEmployee(assignmentDTO.getEmployee()),
                assignmentDTO.getAssignmentType()
        );
    }

    private Request buildRequest(RequestDTO requestDTO) {
        return new Request(
                requestDTO.getId(),
                requestDTO.getAdditional(),
                requestDTO.getReason(),
                buildRequestType(requestDTO.getRequestType()),
                buildAccount(requestDTO.getAccountInfo()),
                buildCountingPoint(requestDTO.getCountingPointAssignment())
        );
    }

    private RequestType buildRequestType(RequestTypeDTO requestTypeDTO) {
        return new RequestType(
                requestTypeDTO.getId(),
                requestTypeDTO.getShortName(),
                requestTypeDTO.getFullName()
        );
    }

    private CountingPoint buildCountingPoint(AccountInfoToCountingPointDTO countingPointAssignment) {
        if (countingPointAssignment == null)
            return null;
        final CountingPointDTO cPoint = countingPointAssignment.getCountingPoint();
        return new CountingPoint(
                cPoint.getKey().getCounterNumber(),
                buildCounterType(cPoint.getCounterType()),
                cPoint.getTpName(),
                cPoint.getFeederNumber(),
                cPoint.getPillarNumber(),
                cPoint.getPower(),
                cPoint.getCheckYear(),
                cPoint.getCheckQuarter()
        );
    }

    private CounterType buildCounterType(CounterTypeDTO counterType) {
        return new CounterType(
                counterType.getId(),
                counterType.getName(),
                counterType.getAccuracy(),
                counterType.getBits(),
                counterType.getSinglePhased()
        );
    }

    private Account buildAccount(AccountInfoDTO accountInfoDTO) {
        if (accountInfoDTO == null)
            return null;
        return new Account(
                accountInfoDTO.getBaseId(),
                accountInfoDTO.getName(),
                buildStreet(accountInfoDTO.getStreet()),
                accountInfoDTO.getHomeNumber(),
                accountInfoDTO.getApartmentNumber(),
                accountInfoDTO.getPhoneNumber()
        );
    }

    private Street buildStreet(StreetDTO street) {
        return new Street(
                street.getId(),
                street.getName(),
                buildDistrict(street.getDistrict())
        );
    }

    private District buildDistrict(DistrictDTO district) {
        return new District(
                district.getId(),
                district.getName()
        );
    }

    private Employee buildEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO == null)
            return null;
        return new Employee(
                employeeDTO.getId(),
                employeeDTO.getName(),
                employeeDTO.getAccessGroup(),
                buildPosition(employeeDTO.getPosition()),
                employeeDTO.getStatus()
        );
    }

    private Position buildPosition(PositionDTO position) {
        return new Position(
                position.getId(),
                position.getName()
        );
    }

    @Override
    public RequestSet updateRequestSet(RequestSet updatedRequestSet) {
        RequestSetDTO dto = updatedRequestSet.getId() == null ? new RequestSetDTO() :
                requestSetRepository.findById(updatedRequestSet.getId()).orElse(new RequestSetDTO());

        dto.setId(updatedRequestSet.getId());
        dto.setName(updatedRequestSet.getName());
        dto.setDate(updatedRequestSet.getDate());

        return buildShortRequestSet(requestSetRepository.save(dto));
    }

    @Override
    public void deleteRequestSet(Long worksheet) {
        if (requestSetRepository.existsById(worksheet)) {
            requestSetRepository.deleteById(worksheet);
        }
    }

    @Transactional
    @Override
    public void moveRequest(Long targetId, List<Long> requests) {
        requestSetRepository
                .findById(targetId)
                .ifPresent(requestSetDTO -> requestRepository.findAllById(requests)
                        .forEach(requestDTO -> requestDTO.setRequestSet(requestSetDTO)));
    }

    @Transactional
    @Override
    public void assignEmployee(Long requestSetId, Integer employeeId, EmployeeAssignmentType type) {
        requestSetRepository.findById(requestSetId)
                .ifPresent(requestSet -> employeeRepository
                        .findById(employeeId)
                        .ifPresent(employeeDTO -> {
                                    var key = new EmployeeAssignmentKey(requestSetId, employeeId);
                                    var existingAssignments = requestSet.getAssignments();

                                    if (type == EmployeeAssignmentType.CHIEF || type == EmployeeAssignmentType.MAIN) {
                                        // If we already have main or chief assignments, delete previous values
                                        employeeAssignmentRepository.deleteAll(
                                                existingAssignments.stream()
                                                        .filter(assignmentDTO -> assignmentDTO
                                                                .getAssignmentType()
                                                                .equals(type)
                                                        )
                                                        .collect(Collectors.toList())
                                        );
                                    }

                                    // Assign or reassign the employee
                                    var assignment = new EmployeeAssignmentDTO();
                                    assignment.setAssignmentKey(key);
                                    assignment.setAssignmentType(type);
                                    assignment.setEmployee(employeeDTO);
                                    assignment.setRequestSet(requestSet);
                                    employeeAssignmentRepository.save(assignment);
                                }
                        )
                );
    }

    @Override
    public void detachEmployee(Long requestSetId, Integer employeeId) {
        var key = new EmployeeAssignmentKey(requestSetId, employeeId);
        if (employeeAssignmentRepository.existsById(key)) {
            employeeAssignmentRepository.deleteById(key);
        }
    }

    @Transactional
    private Request updateRequest(RequestDTO requestDTO, Request inputRequest) {
        requestDTO.setAdditional(inputRequest.getAdditional());
        requestDTO.setReason(inputRequest.getReason());

        var requestType = inputRequest.getRequestType();
        requestDTO.setRequestType(new RequestTypeDTO(
                requestType.getId(),
                requestType.getShortName(),
                requestType.getFullName()
        ));

        var accountInfo = inputRequest.getAccountInfo();
        if (accountInfo != null) {
            // Update account info
            var accountDTO = accountService.updateAccount(accountInfo);
            requestDTO.setAccountInfo(accountDTO);

            var countingPoint = inputRequest.getCountingPoint();
            if (countingPoint != null) {
                // Update counting point
                var countingPointDTO = countingPointService.updateCountingPoint(accountDTO, countingPoint);

                requestDTO.setCountingPointAssignment(countingPointDTO);
            } else {
                requestDTO.setCountingPointAssignment(null);
            }
        } else {
            requestDTO.setCountingPointAssignment(null);
            requestDTO.setAccountInfo(null);
        }
        return buildRequest(requestRepository.save(requestDTO));
    }

}
