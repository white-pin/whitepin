package com.github.whitepin.server.api.service;

import com.github.whitepin.server.api.dto.JoinDTO;
import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.entity.RoleEntity;
import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.repository.RoleRepository;
import com.github.whitepin.server.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    public UserDTO getUserInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntityUserDTOConverter.convert(userRepository.findByEmail(userDetails.getUsername()));
    }
}
