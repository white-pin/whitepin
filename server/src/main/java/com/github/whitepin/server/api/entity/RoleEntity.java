package com.github.whitepin.server.api.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "wp_role"
        , indexes = {
        @Index(columnList="role", unique = true)
}
)
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    long id;

    @Column(name = "role")
    String role;

}
