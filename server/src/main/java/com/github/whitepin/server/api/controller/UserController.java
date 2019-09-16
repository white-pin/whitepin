package com.github.whitepin.server.api.controller;

import com.github.whitepin.server.api.dto.PartnerDTO;
import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.service.UserService;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<UserDTO> getUserInfo() throws Exception {
        return ResponseEntity.ok().body(userService.getUserInfo());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/user/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get total user count")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok().body(userService.getUserCount());
    }

    @PostMapping(value = "/user/joinPartner/{partnerCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "User join Partner", authorizations = {@Authorization(value = "BasicAuth")})
    @ApiRoleAccessNotes
    public ResponseEntity<UserDTO> joinPartner(@Valid
                                                   @ApiParam("Non-empty partnerCode") @PathVariable(name = "partnerCode", required = true) String partnerCode) {
        return ResponseEntity.ok().body(userService.joinPartner(partnerCode));
    }
}
