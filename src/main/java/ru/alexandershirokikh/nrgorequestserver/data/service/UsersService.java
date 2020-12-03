package ru.alexandershirokikh.nrgorequestserver.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alexandershirokikh.nrgorequestserver.data.dao.UsersRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.AuthorityDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.UserDTO;
import ru.alexandershirokikh.nrgorequestserver.models.UserAccount;
import ru.alexandershirokikh.nrgorequestserver.models.UserAuthority;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service responsible for user management
 */
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registers new user or updates existing and returns {@literal true}
     * if registration was successful or {@literal false} on any error
     */
    @Transactional
    public String registerUser(UserAccount account) {
        if (account.getAuthority() == UserAuthority.GUEST || account.getAuthority() == UserAuthority.ADMIN)
            return "Authority check failed!";

        Optional<UserDTO> userDTO = usersRepository.findById(account.getName());

        if (userDTO.isPresent()) {
            // Update existing
            final var user = userDTO.get();

            fillDTO(user, account);

            AuthorityDTO authorityDTO = new AuthorityDTO();
            authorityDTO.setUserAuthority(account.getAuthority());
            authorityDTO.setUser(user);

            final var authorities = user.getAuthorities();
            authorities.clear();
            authorities.add(authorityDTO);
            usersRepository.save(user);
        } else {
            /// Register new user
            UserDTO newUserDTO = new UserDTO();
            newUserDTO.setEnabled(true);
            fillDTO(newUserDTO, account);


            AuthorityDTO authorityDTO = new AuthorityDTO();
            authorityDTO.setKey(new AuthorityDTO.AuthorityPK(account.getName(), account.getAuthority().getName()));
            authorityDTO.setUser(newUserDTO);
            authorityDTO.setUserAuthority(account.getAuthority());
            newUserDTO.getAuthorities().add(authorityDTO);

            usersRepository.save(newUserDTO);
        }
        return null;
    }

    private void fillDTO(UserDTO dto, UserAccount account) {
        dto.setUsername(account.getName());

        if (account.getPassword() != null) {
            dto.setPassword(passwordEncoder.encode(account.getPassword()));
        }
    }

    /**
     * Deletes user by its user name
     */
    public void deleteUser(String username) {
        if (usersRepository.existsById(username)) {
            usersRepository.deleteById(username);
        }
    }

    /**
     * Returns list of all users
     */
    @Transactional
    public List<UserAccount> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(userDTO -> new UserAccount(
                        userDTO.getUsername(),
                        userDTO.getAuthorities().stream().findFirst().orElseThrow().getUserAuthority(),
                        null
                ))
                .filter(account -> account.getAuthority() != UserAuthority.ADMIN)
                .collect(Collectors.toList());
    }

}
