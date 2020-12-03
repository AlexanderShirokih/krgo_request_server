package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.UserDTO;

/**
 * Repository for accessing User objects
 *
 * @see UserDTO
 */
public interface UsersRepository extends JpaRepository<UserDTO, String> {
}
