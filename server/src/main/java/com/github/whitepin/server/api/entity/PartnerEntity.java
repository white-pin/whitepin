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
        name = "wp_partner"
        , indexes = {
        @Index(columnList="partner_code", unique = true)
}
)
public class PartnerEntity {

    @Id
    @Column(name = "partner_code")
    String code;

    @Column(name = "partner_name")
    String name;

}
