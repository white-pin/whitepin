package com.github.whitepin.server.api.controller;

import com.github.whitepin.server.api.dto.PartnerDTO;
import com.github.whitepin.server.api.service.PartnerService;
import com.github.whitepin.server.config.security.ApiRoleAccessNotes;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
public class PartnerController {

    @Autowired
    PartnerService partnerService;

    @RequestMapping(method = RequestMethod.GET, value = "/partners", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all partner list")
    @ApiRoleAccessNotes
    public ResponseEntity<List<PartnerDTO>> getAllPartner() {
        return ResponseEntity.ok().body(partnerService.getAllPartner());
    }

}
