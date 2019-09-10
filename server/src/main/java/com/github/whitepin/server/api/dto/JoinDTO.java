package com.github.whitepin.server.api.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class JoinDTO {

    @NotNull
    @NotBlank
    String name;

    @Email()
    String email;

    String userToken;

    String password;

    String rePassword;

    String phoneNumber;

    String ci;

    String di;
}
