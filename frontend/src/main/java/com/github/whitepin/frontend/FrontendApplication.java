package com.github.whitepin.frontend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
        beforeApplicationSetup(args);
        SpringApplication.run(FrontendApplication.class, args);
    }

    private static void beforeApplicationSetup(String[] args) {
        checkActiveProfile();
        overrideConfigs(args);
    }

    /**
     * profile을 설정하지 않은 경우 기본 값 "local"로 설정
     */
    private static void checkActiveProfile() {
        final String defaultProfile = "local";
        String profileValues = System.getProperty("spring.profiles.active");

        if (!StringUtils.hasText(profileValues)) {
            logger.info("Setting default profile because empty active profile. default : [{}]", defaultProfile);
            System.setProperty("spring.profiles.active", defaultProfile);
            return;
        }

        Set<String> requiredProfileSet = new HashSet<>();
        requiredProfileSet.add("local");
        requiredProfileSet.add("dev");

        String[] profiles = profileValues.split(",");
        for (String profile : profiles) {
            // local , dev, real 중 하나가 있는 경우
            if (requiredProfileSet.contains(profile)) {
                return;
            }
        }

        profileValues = profileValues + "," + defaultProfile;
        System.setProperty("spring.profiles.active", profileValues);
    }

    /**
     * args 중 외부 config 파일이 존재하면 기존 설정에 오버라이딩
     *
     * e.g)
     * --spring.config.location=/home/app/application.yaml일 경우
     * --spring.config.location=classpath:/application.yaml,/home/app/application.yaml 로 변경
     */
    static void overrideConfigs(String[] args) {
        if (args == null || args.length == 0) {
            return;
        }

        final String prefix = "--spring.config.location=";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (!arg.startsWith(prefix)) {
                continue;
            }

            args[i] = prefix + "classpath:/application.yaml," + arg.substring(prefix.length());
            return;
        }
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
