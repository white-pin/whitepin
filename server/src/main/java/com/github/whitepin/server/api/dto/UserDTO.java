package com.github.whitepin.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String mail;
    private String password;
    private String lastName;
    private String name;
    private String address;
    private String username;

}
