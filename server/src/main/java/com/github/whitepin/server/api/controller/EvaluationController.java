package com.github.whitepin.server.api.controller;

import com.github.whitepin.server.api.dto.EvaluationAverageDTO;
import com.github.whitepin.server.api.dto.EvaluationListDTO;
import com.github.whitepin.server.api.service.EvaluationService;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @RequestMapping(method = RequestMethod.GET, value = "/evaluation/{userToken}/avg", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get evaluation average")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the given user evaluation average"),
    })
    @ApiRoleAccessNotes
    public ResponseEntity<EvaluationAverageDTO> getUserEvaluationAverage(@Valid
                                                                         @ApiParam("Non-empty userToken") @PathVariable(name = "userToken", required = true) String userToken) {
        return ResponseEntity.ok().body(evaluationService.getUserEvaluationAverage(userToken));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evaluation/List/{userToken}/{filterDivision}/{pagingSize}/{pageNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get evaluation list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Details about the given user evaluation list"),
    })
    @ApiRoleAccessNotes
    public ResponseEntity<EvaluationListDTO> getUserEvaluationList(@Valid
                                                                   @ApiParam("Non-empty userToken") @PathVariable(name = "userToken", required = true) String userToken
            , @ApiParam("Non-empty filterDivision") @PathVariable(name = "filterDivision", required = true) String filterDivision
            , @ApiParam("Non-empty pagingSize") @PathVariable(name = "pagingSize", required = true) String pagingSize
            , @ApiParam("Non-empty pageNumber") @PathVariable(name = "pageNumber", required = true) String pageNumber
    ) {
        return ResponseEntity.ok().body(evaluationService.getUserEvaluationList(
                userToken
                , filterDivision
                , pagingSize
                , pageNumber
        ));
    }


}
