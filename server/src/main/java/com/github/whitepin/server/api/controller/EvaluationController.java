package com.github.whitepin.server.api.controller;

import com.github.whitepin.server.api.dto.EvaluationAverageDTO;
import com.github.whitepin.server.api.dto.EvaluationListDTO;
import com.github.whitepin.server.api.service.EvaluationService;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping(method = RequestMethod.GET, value = "/evaluation/avg/{userToken}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get evaluation average")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the given user evaluation average"),
    })
    @ApiRoleAccessNotes
    public ResponseEntity<EvaluationAverageDTO> getUserEvaluationAverage(@Valid
                                                                         @ApiParam("Non-empty userToken") @PathVariable(name = "userToken", required = true) String userToken) throws Exception {
        return ResponseEntity.ok().body(evaluationService.getUserEvaluationAverage(userToken));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evaluation/list/{userToken}/{filterDivision}/{pageNumber}/{orderType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get evaluation list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the given user evaluation list"),
    })
    @ApiRoleAccessNotes
    public ResponseEntity<EvaluationListDTO> getTargetUserEvaluationList(@Valid
                                                                         @ApiParam("Non-empty userToken") @PathVariable(name = "userToken", required = true) String userToken
            , @ApiParam("Non-empty filterDivision value (buy or sell) ") @PathVariable(name = "filterDivision", required = true) String filterDivision
            , @ApiParam("Non-empty pageNumber") @PathVariable(name = "pageNumber", required = true) String pageNumber
            , @ApiParam("Non-empty orderType value (asc or desc, default:desc)") @PathVariable(name = "orderType", required = true) String orderType
    ) throws Exception {
        return ResponseEntity.ok().body(evaluationService.getUserEvaluationList(
                userToken
                , filterDivision
                , pageNumber
                , orderType
                , true
        ));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evaluation/myList/{filterDivision}/{pageNumber}/{orderType}/{isEvaluationReceived}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get evaluation list", authorizations = {@Authorization(value = "BasicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the given user evaluation list"),
            @ApiResponse(code = 401, message = "Cannot authenticate"),
            @ApiResponse(code = 403, message = "Not authorized to get details about the given user evaluation list")
    })
    @ApiRoleAccessNotes
    public ResponseEntity<EvaluationListDTO> getUserEvaluationList(@Valid
                                                                       @ApiParam("Non-empty filterDivision value (buy or sell) ") @PathVariable(name = "filterDivision", required = true) String filterDivision
            , @ApiParam("Non-empty pageNumber") @PathVariable(name = "pageNumber", required = true) String pageNumber
            , @ApiParam("Non-empty orderType value (asc or desc, default:desc)") @PathVariable(name = "orderType", required = true) String orderType
            , @ApiParam("Non-empty isEvaluationReceived value (true or false") @PathVariable(name = "isEvaluationReceived", required = true) boolean isEvaluationReceived
    ) throws Exception {
        return ResponseEntity.ok().body(evaluationService.getMyEvaluationList(
                filterDivision
                , pageNumber
                , orderType
                , isEvaluationReceived
        ));
    }
}
