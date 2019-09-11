package com.github.whitepin.server.api.component;

import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.service.security.UserDetailsServiceImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserEntityToUserDetails implements Converter<UserEntity, UserDetails> {

    @Override
    public UserDetails convert(UserEntity user) {
        UserDetailsServiceImpl userDetails = new UserDetailsServiceImpl();
        if (user != null) {
            userDetails.setUsername(user.getEmail());
            userDetails.setPassword(user.getPassword());
            userDetails.setEnabled("Y".equals(user.getUseYn()));
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            });
            userDetails.setAuthorities(authorities);
        }
        return userDetails;
    }

}
