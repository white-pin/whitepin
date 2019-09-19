package com.github.whitepin.server.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "batch")
public class BatchProperty {

    private TxSync txSync;

    @Getter
    @Setter
    public static class TxSync {

        private boolean enable = false;
        private long initDelay = 10000L;
        private long period = 10000L;
    }
}
