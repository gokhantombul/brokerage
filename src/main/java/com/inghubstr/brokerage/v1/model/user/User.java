package com.inghubstr.brokerage.v1.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inghubstr.brokerage.v1.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "ALL_USERS")
public class User extends BaseEntity {

    public User(Long id) {
        this.id = id;
    }

    @Column(length = 512, nullable = false)
    private String firstName;

    @Column(length = 512, nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 1024)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean tokenExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @JsonBackReference
    private Collection<Role> roleList;

}
