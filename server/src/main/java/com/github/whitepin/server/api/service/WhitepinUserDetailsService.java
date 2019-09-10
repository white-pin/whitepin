package com.github.whitepin.server.api.service;

import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WhitepinUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Converter<UserEntity, UserDetails> userUserDetailsConverter;

    @Autowired
    @Qualifier(value = "userEntityToUserDetails")
    public void setUserUserDetailsConverter(Converter<UserEntity, UserDetails> userUserDetailsConverter) {
        this.userUserDetailsConverter = userUserDetailsConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userUserDetailsConverter.convert(userRepository.findByEmail(email));
    }
}