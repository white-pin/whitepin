package com.github.whitepin.server.api.controller;

import com.github.whitepin.server.api.dto.JoinDTO;
import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.service.JoinService;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class JoinController {

    @Autowired
    JoinService joinService;

    @PostMapping(value = "/join", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Join a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully created"),
    })
    @ApiRoleAccessNotes
    public ResponseEntity<UserDTO> joinUser(@Valid @RequestBody JoinDTO joinDTO) {
        return ResponseEntity.ok().body(joinService.joinUser(joinDTO));
    }
}
