package com.github.whitepin.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SpringBootApplication
public class FrontendApplication {

    @Value("${api.end-points}")
    private String apiEndPoints;

    private Map<String, Object> apiInfo;

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @PostConstruct
    private void setUp() {
        apiInfo = new HashMap<>();
        apiInfo.put("endpoints", apiEndPoints);
    }

    @GetMapping(value = "/api-info", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        return ResponseEntity.ok(apiInfo);
    }

    @GetMapping("/")
    public String indexPage() {
        logger.info("Request index page");
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        logger.info("Request login page");
        return "login";
    }

    @GetMapping("/join")
    public String joinPage() {
        logger.info("Request join page");
        return "join";
    }

    // TODO : temp
    @GetMapping("/backup/join-success")
    public String joinSuccessPage() {
        logger.info("Request join success page");
        return "backup/join_success";
    }

    @GetMapping("/backup/temp")
    public String tempPage() {
        return "temp";
    }
    // -- TODO : temp

    @GetMapping("/notification")
    public String notificationPage() {
        logger.info("Request notification page");
        return "notification";
    }

    @GetMapping("/accounts/edit")
    public String accountsEditPage() {
        logger.info("Request account edit page");
        return "account_edit";
    }

    @GetMapping("/account/{userHash}")
    public String accountSearch(@PathVariable("userHash") String userHash) {
        logger.info("Request account search page : {}", userHash);
        return "account";
    }
}
