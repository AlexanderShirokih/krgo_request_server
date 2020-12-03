package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.*;
import ru.alexandershirokikh.nrgorequestserver.models.UserAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "authorities")
public class AuthorityDTO implements Serializable {

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorityPK implements Serializable {
        @Column
        private String username;

        @Basic
        private String authority;
    }

    @EmbeddedId
    private AuthorityPK key;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "username", nullable = false, insertable = false, updatable = false)
    @MapsId("username")
    private UserDTO user;


    @Transient
    private UserAuthority userAuthority;

    @PostLoad
    void fillTransient() {
        if (key != null && key.getAuthority() != null) {
            this.userAuthority = UserAuthority.fromString(key.getAuthority());
        }
    }

    @PrePersist
    void fillPersistent() {
        if (userAuthority != null) {
            this.getKey().setAuthority(userAuthority.getName());
        }
    }

}
