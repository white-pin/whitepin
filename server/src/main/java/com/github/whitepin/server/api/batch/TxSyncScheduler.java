package com.github.whitepin.server.api.batch;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;
import com.github.whitepin.sdk.whitepin.vo.ScoreVo;
import com.github.whitepin.server.api.constants.AppConstants;
import com.github.whitepin.server.config.property.BatchProperty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Whitepin 체인코드의 임시 점수 데이터 중 기한이 만료 된 점수 -> Trade 점수로 동기화
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "batch.txSync.enable", havingValue = "true")
@RequiredArgsConstructor
public class TxSyncScheduler {

    // beans
    private final BatchProperty batchProperty;
    private final FabricContruct fabricContruct;
    private final ChaincodeInvocation chaincodeInvocation;

    private ScheduledExecutorService scheduledExecutor;

    @PostConstruct
    private void setUp() {
        start();
    }

    public void start() {
        logger.info("Start tx synchronize batch");
        if (isRunning()) {
            logger.warn("Already tx sync batch is running");
            return;
        }

        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        long initDelay = batchProperty.getTxSync().getInitDelay();
        long period = batchProperty.getTxSync().getPeriod();

        Assert.isTrue(initDelay > 0L, "initDelay must larger than 0L");
        Assert.isTrue(period > 0L, "period must larger than 0L");

        scheduledExecutor.scheduleAtFixedRate(this::synchronizeTx, initDelay, period, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (!isRunning()) {
            logger.debug("Already tx sync batch is stopped");
            return;
        }

        // thread pool shutdown
        scheduledExecutor.shutdown();

        try {
            scheduledExecutor.awaitTermination(3000, TimeUnit.MILLISECONDS);
            logger.debug("Stopped tx sync batch");
        } catch (InterruptedException e) {
        }
    }

    public boolean isRunning() {
        return (scheduledExecutor != null)
               && (!scheduledExecutor.isShutdown())
               && (!scheduledExecutor.isTerminated());
    }

    /**
     * batch timeout 지난 임시 score -> Trade 동기화
     */
    private void synchronizeTx() {
        try {
            final Channel channel = fabricContruct.getChannel();
            final HFClient hfClient = fabricContruct.getClient();
            List<ScoreVo> tempScores = chaincodeInvocation.queryScoreTempWithExpired(channel, hfClient);

            if (CollectionUtils.isEmpty(tempScores)) {
                return;
            }

            List<ScoreVo> updateTargets = tempScores.stream()
                                                    .filter(temp -> temp.isExpired())
                                                    .collect(Collectors.toList());

            int failedCount = 0;

            for (ScoreVo updateTarget : updateTargets) {
                try {
                    boolean result = chaincodeInvocation.enrollScore(channel, hfClient,
                                                                     updateTarget.getTradeId(),
                                                                     AppConstants.TEMP_SCORE_ENC_KEY);
                    if (!result) {
                        throw new Exception("failed to migrate temporary score " + updateTarget.getTradeId());
                    }
                } catch (Exception e) {
                    logger.warn("Exception occur while migration temp scores. reason : {}", e.getMessage());
                    failedCount++;
                }
            }

            logger.info("Result of migration temporary scores -> temp scores : {} / expired : {} / failed : {}"
                    , tempScores.size(), updateTargets.size(), failedCount);
        } catch (Exception e) {
            logger.warn("Exception occur while synchronize temporary score");
        }
    }
}
