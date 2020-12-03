package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Describes user authorities
 */
@Getter
@RequiredArgsConstructor
public enum UserAuthority {
    GUEST(null),
    USER("ROLE_USER"),
    MODERATOR("ROLE_MODERATE"),
    ADMIN("ROLE_ADMIN");

    /**
     * User authority name used in the database.
     */
    final String name;

    /**
     * Returns {@link UserAuthority} constant based on authorityName. If corresponding {@link UserAuthority} was not found
     * GUEST will be used.
     */
    public static UserAuthority fromString(String authorityName) {
        for (var authority : UserAuthority.values()) {
            if (Objects.equals(authority.name, authorityName)) return authority;
        }
        return UserAuthority.GUEST;
    }
}
