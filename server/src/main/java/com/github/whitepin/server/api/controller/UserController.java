package com.github.whitepin.server.api.controller;

import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.service.UserService;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/user/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "User withdrawal", authorizations = {@Authorization(value = "BasicAuth")})
    @ApiRoleAccessNotes
    public ResponseEntity<UserDTO> userWithdraw() {
        return ResponseEntity.ok().body(userService.userWithdraw());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get details of a user", authorizations = {@Authorization(value = "BasicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the given user"),
            @ApiResponse(code = 401, message = "Cannot authenticate"),
            @ApiResponse(code = 403, message = "Not authorized to get details about the given user")
    })
    @ApiRoleAccessNotes
    public ResponseEntity<UserDTO> getUserInfo() {
        return ResponseEntity.ok().body(userService.getUserInfo());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/user/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get total user count")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok().body(userService.getUserCount());
    }
}
