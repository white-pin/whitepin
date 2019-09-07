package com.github.whitepin.server;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // TODO : will removed
@SpringBootApplication
public class WhitePinServerApplication {

    // TODO : will removed
    @GetMapping("/")
    public String hello() {
        return "Hello white pin server :)\n";
    }
    // -- TODO : will removed

    public static void main(String[] args) {
        beforeApplicationSetup(args);
        SpringApplication.run(WhitePinServerApplication.class, args);
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
}
