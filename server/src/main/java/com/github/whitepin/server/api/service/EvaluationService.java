package com.github.whitepin.server.api.service;

import com.github.whitepin.server.api.dto.EvaluationAverageDTO;
import com.github.whitepin.server.api.dto.EvaluationListDTO;
import com.github.whitepin.server.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EvaluationService {

    @Autowired
    private UserRepository userRepository;

    public EvaluationAverageDTO getUserEvaluationAverage(String userToken) {
        return EvaluationAverageDTO.builder()
                .userToken(userToken)
                .evaluationBuy(
                        EvaluationAverageDTO.Average.builder()
                                .division("1")
                                .A("1")
                                .B("1")
                                .C("1")
                                .totalAverage("1")
                                .build())
                .evaluationSell(
                        EvaluationAverageDTO.Average.builder()
                                .division("2")
                                .A("1")
                                .B("1")
                                .C("1")
                                .totalAverage("1")
                                .build())
                .evaluationSum(
                        EvaluationAverageDTO.Average.builder()
                                .division("3")
                                .A("1")
                                .B("1")
                                .C("1")
                                .totalAverage("1")
                                .build())
                .evaluationWhitepin(
                        EvaluationAverageDTO.Average.builder()
                                .division("4")
                                .A("2")
                                .B("2")
                                .C("2")
                                .totalAverage("2")
                                .build())
                .build();
    }

    public EvaluationListDTO getUserEvaluationList(String userToken, String filterDivision, String pagingSize, String currentPageNumber) {
        return EvaluationListDTO.builder()
                .userToken(userToken)
                .filterDivision(filterDivision)
                .pagingSize(pagingSize)
                .currentPageNumber(currentPageNumber)
                .transaction(
                        Arrays.asList(
                                EvaluationListDTO.Transaction.builder()
                                        .division("1")
                                        .transactionHash("0xaaaaaaaaaaaaaaa")
                                        .targetUserToken("0xbbbbbbbbbbbbbbbbb")
                                        .A("2")
                                        .B("2")
                                        .C("2")
                                        .evaluationDate("2019-06-01")
                                        .partnerCode("PTN0001")
                                        .build()
                                , EvaluationListDTO.Transaction.builder()
                                        .division("2")
                                        .transactionHash("0xccccccccccccccc")
                                        .targetUserToken("0xdddddddddddddd")
                                        .A("3")
                                        .B("3")
                                        .C("3")
                                        .evaluationDate("2019-06-02")
                                        .partnerCode("PTN0002")
                                        .build()
                        ))
                .build();
    }

}
