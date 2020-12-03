package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.alexandershirokikh.nrgorequestserver.data.service.UsersService;
import ru.alexandershirokikh.nrgorequestserver.models.ErrorWrapper;
import ru.alexandershirokikh.nrgorequestserver.models.UserAccount;
import ru.alexandershirokikh.nrgorequestserver.models.UserAuthority;

import javax.validation.Valid;
import java.util.List;

/**
 * Responsible for user management. Returns information about currently registered users
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    /**
     * Returns info about current user
     */
    @GetMapping("/current")
    public UserAccount getUserInfo(@AuthenticationPrincipal Authentication username) {
        return new UserAccount(
                username.getName(),
                extractAuthority(username),
                null
        );
    }

    /**
     * Returns all users except of admins
     */
    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllUsers(@AuthenticationPrincipal Authentication requester) {
        if (!requester.isAuthenticated() || extractAuthority(requester) != UserAuthority.ADMIN) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(usersService.getAllUsers());
    }

    /**
     * Creates new user in the database. Registrar should have ADMIN authorities.
     * If user already its fields will be updated.
     */
    @PostMapping
    public ResponseEntity<ErrorWrapper> register(@AuthenticationPrincipal Authentication registrar, @RequestBody @Valid UserAccount newUser) {
        if (!registrar.isAuthenticated() || extractAuthority(registrar) != UserAuthority.ADMIN) {
            return ResponseEntity.status(401).build();
        }

        String errors = usersService.registerUser(newUser);
        if (errors == null || errors.isEmpty())
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().body(new ErrorWrapper("Bad Request", errors));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Authentication registrar, @PathVariable("username") String username) {
        if (!registrar.isAuthenticated() || extractAuthority(registrar) != UserAuthority.ADMIN) {
            return ResponseEntity.status(401).build();
        }

        usersService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    private UserAuthority extractAuthority(Authentication authentication) {
        return !authentication.isAuthenticated()
                ? UserAuthority.GUEST
                : authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(UserAuthority::fromString)
                .orElse(UserAuthority.GUEST);
    }
}
