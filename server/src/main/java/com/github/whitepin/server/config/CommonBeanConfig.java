package com.github.whitepin.server.config;

import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocationImpl;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonBeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Keccak.Digest256 sha3() {
        return new Keccak.Digest256();
    }

    @Bean
    public FabricContruct fabricContruct() throws Exception {
        FabricContruct fabricContruct = new FabricContruct();
        fabricContruct.setUp();
        return fabricContruct;
    }

    @Bean
    public ChaincodeInvocation chaincodeInvocation() {
        return new ChaincodeInvocationImpl();
    }

}
