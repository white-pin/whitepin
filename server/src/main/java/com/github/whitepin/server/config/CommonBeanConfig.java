package com.github.whitepin.server.config;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocationImpl;
import com.github.whitepin.server.config.property.FabricProperties;

@Configuration
public class CommonBeanConfig {

    @Autowired
    private FabricProperties fabricProperties;

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
        FabricContruct fabricContruct = new FabricContruct(
                fabricProperties.getCaName(), fabricProperties.getCaLocation(), fabricProperties.getUserName()
                , fabricProperties.getUserPassword(), fabricProperties.getOrgMsp(),
                fabricProperties.getOrdererName(), fabricProperties.getOrdererLocation(),
                fabricProperties.getPeerName1(), fabricProperties.getPeerLocation1(),
                fabricProperties.getChannelName()
        );
        fabricContruct.setUp();
        return fabricContruct;
    }

    @Bean
    public ChaincodeInvocation chaincodeInvocation() {
        return new ChaincodeInvocationImpl();
    }
}
