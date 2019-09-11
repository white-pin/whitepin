package com.github.whitepin.server.api.controller;

import com.github.whitepin.server.api.dto.JoinDTO;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
//@RestController
public class MyRestController {

//    @RequestMapping(method = RequestMethod.GET, value = "/user/{userName}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get details of a user with the given username", authorizations = {@Authorization(value = "BasicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the given user"),
            @ApiResponse(code = 401, message = "Cannot authenticate"),
            @ApiResponse(code = 403, message = "Not authorized to get details about the given user")
    })
    @ApiRoleAccessNotes
    public JoinDTO getExampleData(@Valid
                                  @ApiParam("Non-empty userName") @PathVariable(name = "userName", required = true) String userName) {
        logger.debug("Getting user with username '%'", userName);
        return JoinDTO.builder()
                .email(userName + "@aaaaaa.com")
                .name(userName)
                .password("****")
                .build();
    }

//    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new user or update an existing user (based on username)", authorizations = {@Authorization(value = "BasicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully updated"),
            @ApiResponse(code = 200, message = "User was successfully created"),
            @ApiResponse(code = 401, message = "Cannot authenticate"),
            @ApiResponse(code = 403, message = "Not authorized to create or update users")
    })
    @ApiRoleAccessNotes
    public void createUser(@Valid @RequestBody JoinDTO joinDTO) {
        logger.debug("Creating user with username '%'", joinDTO.getName());
    }
}
