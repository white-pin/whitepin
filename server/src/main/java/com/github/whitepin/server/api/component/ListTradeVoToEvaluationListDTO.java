package com.github.whitepin.server.api.component;

import com.github.whitepin.sdk.whitepin.vo.TradeVo;
import com.github.whitepin.server.api.dto.EvaluationListDTO;
import com.github.whitepin.server.api.entity.PartnerEntity;
import com.github.whitepin.server.api.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class ListTradeVoToEvaluationListDTO implements Converter<List<TradeVo>, EvaluationListDTO> {

    @Autowired
    PartnerRepository partnerRepository;

    @Override
    public EvaluationListDTO convert(List<TradeVo> tradeVoList) {
        return null;
    }

    public EvaluationListDTO convert(String filterDivision, List<TradeVo> tradeVoList, boolean isEvaluationReceived) {
        EvaluationListDTO evaluationListDTO = new EvaluationListDTO();
        if (!CollectionUtils.isEmpty(tradeVoList)) {
            List<EvaluationListDTO.Transaction> transactionList = evaluationListDTO.getTransaction();
            tradeVoList.forEach(tradeVo -> {
                int[] score = getScore(filterDivision, tradeVo.getScore(), isEvaluationReceived);
                String targetUserToken = getTargetUserToken(filterDivision, tradeVo, isEvaluationReceived);
                PartnerEntity partnerEntity = partnerRepository.findByCode(tradeVo.getServiceCode());
                transactionList.add(EvaluationListDTO.Transaction.builder()
                        .division(filterDivision)
                        .transactionHash(tradeVo.getTradeId())
                        .targetUserToken(targetUserToken)
                        .A(ObjectUtils.isEmpty(score) || score[0] == 0 ? "-" : String.valueOf(score[0]))
                        .B(ObjectUtils.isEmpty(score) || score[1] == 0 ? "-" : String.valueOf(score[1]))
                        .C(ObjectUtils.isEmpty(score) || score[2] == 0 ? "-" : String.valueOf(score[2]))
                        .evaluationDate(tradeVo.getDate())
                        .partnerCode(ObjectUtils.isEmpty(partnerEntity) ? tradeVo.getServiceCode() : partnerEntity.getName())
                        .build()
                );
            });

        }
        return evaluationListDTO;
    }

    private String getTargetUserToken(String filterDivision, TradeVo tradeVo, boolean isEvaluationReceived){
        return "buy".equals(filterDivision) ? tradeVo.getSellerTkn() : tradeVo.getBuyerTkn();
    }

    private int[] getScore(String filterDivision, TradeVo.Score score, boolean isEvaluationReceived) {
//        buy	TRUE	buyscore
//        buy	FALSE	sellscore
//        sell	TRUE	sellscore
//        sell	FALSE	buyscore

        if("buy".equals(filterDivision)){
            if(isEvaluationReceived){
                return score.getBuyScore();
            } else {
                return score.getSellScore();
            }
        } else {
            if(isEvaluationReceived){
                return score.getSellScore();
            } else {
                return score.getBuyScore();
            }
        }
    }
}
