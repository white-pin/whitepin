package com.github.whitepin.server.api.service;

import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;
import com.github.whitepin.sdk.whitepin.vo.UserVo;
import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FabricService {

    @Autowired
    private FabricContruct fabricContruct;

    @Autowired
    private ChaincodeInvocation chaincodeInvocation;

    public void addUser(String userToken) throws Exception {
        chaincodeInvocation.addUser(fabricContruct.getChannel(), fabricContruct.getClient(), userToken);
    }
}
