package com.github.whitepin.server.api.component;

import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.service.security.UserDetailsServiceImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserEntityToUserDTO implements Converter<UserEntity, UserDTO> {

    @Override
    public UserDTO convert(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO = UserDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .userToken(user.getUserToken())
                    .build();
        }
        return userDTO;
    }

}
