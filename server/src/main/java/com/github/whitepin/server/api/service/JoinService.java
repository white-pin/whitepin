package com.github.whitepin.server.api.service;

import com.github.whitepin.server.api.dto.JoinDTO;
import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.entity.RoleEntity;
import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.repository.RoleRepository;
import com.github.whitepin.server.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Converter<JoinDTO, UserEntity> joinUserEntityConverter;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    @Autowired
    private FabricService fabricService;

    public UserDTO joinUser(JoinDTO joinDTO) throws Exception {
        UserEntity userEntity = joinUserEntityConverter.convert(joinDTO);
        RoleEntity roleUser = roleRepository.findByRole("user");
        userEntity.addRole(roleUser);

        fabricService.addUser(userEntity.getUserToken());

        return userEntityUserDTOConverter.convert(userRepository.save(userEntity));
    }
}
