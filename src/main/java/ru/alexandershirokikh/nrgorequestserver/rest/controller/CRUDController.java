package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Controller that does create, read  and delete operations
 * R - repository foe doing operations on it
 * A - data class that contains request for adding new entities
 * E - entity type
 * <p>
 *     <ul>
 *         <li>Gets list of all entities by GET / request.</li>
 *         <li>Adds or updates new entity by POST / request</li>
 *         <li>Deletes entity with {@literal id} by DELETE /{id} request</li>
 *     </ul>
 * </p>
 */
@RequiredArgsConstructor
public abstract class CRUDController<A, E, R extends JpaRepository<E, Integer>> {

    @Getter
    private final R repository;

    /**
     * Returns list of all entities
     */
    @GetMapping
    List<E> getAll() {
        return repository.findAll();
    }

    /**
     * Creates new entity from given request
     */
    abstract protected E createEntity(A request);

    /**
     * Adds a new entity to the database from given request or ID is {@literal null}
     * or updates existing value if ID is present.
     */
    @PostMapping
    ResponseEntity<E> save(@Valid @RequestBody A request) {
        try {
            E entity = createEntity(request);
            return ResponseEntity.ok(repository.save(entity));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes district with given id
     */
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteEntity(@PathVariable @Positive @NotNull Integer id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
