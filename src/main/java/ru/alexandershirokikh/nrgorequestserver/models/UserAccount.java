package ru.alexandershirokikh.nrgorequestserver.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Contains info about request system users
 */
@Data
@RequiredArgsConstructor
public class UserAccount {

    /**
     * User name
     */
    @NotEmpty
    final String name;

    /**
     * User authority
     */
    @NotNull
    final UserAuthority authority;

    /**
     * User password
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    final String password;
}
