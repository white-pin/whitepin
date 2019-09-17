package com.github.whitepin.server.api.mock.controller;

import com.github.whitepin.server.api.mock.dto.MockIdentificationDTO;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class MockController {

    final String DI_PREFIX = "whitepin";

    @Autowired
    Keccak.Digest256 sha3;

    @PostMapping(value = "/mock/identification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "[MOCK] identification")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "identification successfully"),
    })
    @ApiRoleAccessNotes
    public ResponseEntity<MockIdentificationDTO> identification(@Valid @RequestBody MockIdentificationDTO mockIdentificationDTO) {
        String phoneNumber = mockIdentificationDTO.getPhoneNumber();
        byte[] ciHashbytes = sha3.digest(
                phoneNumber.getBytes(StandardCharsets.UTF_8));
        byte[] diHashbytes = sha3.digest(
                (phoneNumber+DI_PREFIX).getBytes(StandardCharsets.UTF_8));

        mockIdentificationDTO.setCi(bytesToString(ciHashbytes));
        mockIdentificationDTO.setDi(bytesToString(diHashbytes));
        return ResponseEntity.ok().body(mockIdentificationDTO);
    }

    private String bytesToString(byte[] a){
        return new String(Hex.encode(a));
    }
}
