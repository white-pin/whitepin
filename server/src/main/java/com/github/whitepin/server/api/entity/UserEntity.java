package com.github.whitepin.server.api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "wp_user"
        , indexes = {
            @Index(columnList="email", unique = true)
        }
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String ci;

    @Column(nullable = false)
    String di;

    @Column(nullable = false)
    String useYn = "Y";

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wp_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
}
