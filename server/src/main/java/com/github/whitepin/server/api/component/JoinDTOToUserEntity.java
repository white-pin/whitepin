package com.github.whitepin.server.api.component;

import com.github.whitepin.server.api.dto.JoinDTO;
import com.github.whitepin.server.api.entity.UserEntity;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JoinDTOToUserEntity implements Converter<JoinDTO, UserEntity> {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Keccak.Digest256 sha3;

    @Override
    public UserEntity convert(JoinDTO join) {
        UserEntity userEntity = new UserEntity();
        if (join != null) {
            byte[] hashbytes = sha3.digest(
                    join.getEmail().getBytes(StandardCharsets.UTF_8));
            String userToken = new String(Hex.encode(hashbytes));

            userEntity = UserEntity.builder()
                    .name(join.getName())
                    .email(join.getEmail())
                    .password(passwordEncoder.encode(join.getPassword()))
                    .phoneNumber(join.getPhoneNumber())
                    .ci(join.getCi())
                    .di(join.getDi())
                    .userToken(userToken)
                    .useYn("Y")
                    .build();
        }
        return userEntity;
    }

}
