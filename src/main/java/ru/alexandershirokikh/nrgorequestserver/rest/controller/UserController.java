package ru.alexandershirokikh.nrgorequestserver.rest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandershirokikh.nrgorequestserver.models.UserAccount;

/**
 * Responsible for user management. Returns information about currently registered users
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * Returns info about current user
     */
    @GetMapping()
    public UserAccount getUserInfo(@AuthenticationPrincipal Authentication username) {
        System.out.println("AUTH: " + username.getAuthorities().toString());

        return new UserAccount(
                username.getName(),
                username.isAuthenticated() &&
                        username.getAuthorities()
                                .stream()
                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ADMIN_ROLE))
        );
    }

}
