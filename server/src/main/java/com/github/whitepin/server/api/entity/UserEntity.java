package com.github.whitepin.server.api.entity;

import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    String userToken;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String ci;

    @Column(nullable = false)
    String di;

    @Column(nullable = false)
    String useYn = "Y";

    @Column(name = "create_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    java.util.Calendar createDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "wp_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id")
            , indexes = {
                    @Index(columnList="user_id,role_id", unique = true)
            }
    )
    private List<RoleEntity> roles = new ArrayList<>();

    public void addRole(RoleEntity roleEntity){
        if(CollectionUtils.isEmpty(this.roles)){
            this.roles = new ArrayList<>();
        }
        this.roles.add(roleEntity);
    }

}
