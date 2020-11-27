package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandershirokikh.nrgorequestserver.data.dao.EmployeeRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.EmployeeDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.PositionDTO;
import ru.alexandershirokikh.nrgorequestserver.models.Employee;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends CRUDController<Employee, EmployeeDTO, EmployeeRepository> {

    public EmployeeController(EmployeeRepository repository) {
        super(repository);
    }

    @Override
    protected EmployeeDTO createEntity(Employee employee) {
        var dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setAccessGroup(employee.getAccessGroup());
        dto.setStatus(EmployeeDTO.EmployeeStatus.WORKS);

        var positionDTO = new PositionDTO();
        positionDTO.setId(employee.getPosition().getId());
        dto.setPosition(positionDTO);
        return dto;
    }

    @Override
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEntity(@PathVariable("id") @Positive @NotNull Integer id) {
        // Try to delete employee. If we affect foreign key constraints then mark
        // employee as fired
        try {
            return super.deleteEntity(id);
        } catch (DataIntegrityViolationException e) {
            getRepository().findById(id).ifPresent(employee -> {
                employee.setStatus(EmployeeDTO.EmployeeStatus.FIRED);
                getRepository().save(employee);
            });
        }
        return ResponseEntity.ok().build();
    }
}
