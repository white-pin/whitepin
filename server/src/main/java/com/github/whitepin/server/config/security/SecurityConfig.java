package com.github.whitepin.server.config.security;

import com.github.whitepin.server.api.service.security.WhitepinUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Autowired
    private MatchersSecurityConfiguration matchersSecurityConfiguration;

    private static final String[] AUTH_WHITELIST_SPRINGFOX = {
            "/join",
            "/mock/**",
            "/user/count",
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               WhitepinUserDetailsServiceImpl userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST_SPRINGFOX);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.httpBasic();

        matchersSecurityConfiguration.getMatchers().configure(httpSecurity);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
//        auth.inMemoryAuthentication()
//                // user / userpw
//                .withUser("user").password("$2a$10$xaRYL0HbgsM8AH.f1lYnEuUmuqDHHJCc9fpC/F/.W8qHCmnXnd.Bq").roles("user")
//                .and()
//                // admin / adminpw
//                .withUser("admin").password("$2a$10$Q8cgIoqLvufVIkYZLzfi7O6rRi9eNn2/18APAmzRbW9rsA921MJuG").roles("user", "admin");
    }

}
