package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes DTO object of application user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserDTO {

    @Id
    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 68, nullable = false)
    private String password;

    @Column
    private Boolean enabled;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<AuthorityDTO> authorities = new HashSet<>();
}
