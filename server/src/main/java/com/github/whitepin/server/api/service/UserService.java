package com.github.whitepin.server.api.service;

import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;
import com.github.whitepin.sdk.whitepin.vo.UserVo;
import com.github.whitepin.server.api.dto.PartnerDTO;
import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.entity.PartnerEntity;
import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.repository.PartnerRepository;
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
    private PartnerRepository partnerRepository;

    @Autowired
    private FabricContruct fabricContruct;

    @Autowired
    private ChaincodeInvocation chaincodeInvocation;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    public UserDTO userWithdraw() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername());
        userEntity.setUseYn("N");
        UserDTO userDTO = userEntityUserDTOConverter.convert(userRepository.save(userEntity));

        SecurityContextHolder.clearContext();

        return userDTO;
    }

    public UserDTO getUserInfo() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userEntityUserDTOConverter.convert(userRepository.findByEmail(userDetails.getUsername()));

        UserVo userVo = chaincodeInvocation.queryUser(fabricContruct.getChannel(), fabricContruct.getClient(), userDTO.getUserToken());

        int transactionCount = userVo.getSellAmt() + userVo.getBuyAmt();
        userDTO.setTransactionCount(transactionCount);

        int evaluationReceivedCount = (userVo.getSellAmt() + userVo.getBuyAmt()) - (userVo.getSellEx() + userVo.getBuyEx());
        userDTO.setEvaluationReceivedCount(evaluationReceivedCount);

        return userDTO;
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public UserDTO joinPartner(String partnerCode) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername());

        PartnerEntity partnerEntity = partnerRepository.findByCode(partnerCode);
        userEntity.addPartners(partnerEntity);

        return userEntityUserDTOConverter.convert(userRepository.save(userEntity));
    }
}
