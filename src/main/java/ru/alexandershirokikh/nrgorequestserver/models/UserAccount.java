package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

/**
 * Contains info about request system users
 */
@Data
public class UserAccount {

    /**
     * User name
     */
    final String name;

    /**
     * {@literal true} is this user has admin privileges.
     */
    final Boolean hasModerationRights;

}
