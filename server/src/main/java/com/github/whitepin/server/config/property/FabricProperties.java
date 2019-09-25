package com.github.whitepin.server.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "fabric")
public class FabricProperties {
    private String caName;
    private String caLocation;
    private String userName;
    private String userPassword;

    private String orgMsp;

    private String ordererName;
    private String ordererLocation;
    private String peerName1;
    private String peerLocation1;

    private String channelName;
}
