package com.github.whitepin.server.api.service;

import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.context.ConditionType;
import com.github.whitepin.sdk.whitepin.context.OrderType;
import com.github.whitepin.sdk.whitepin.context.ReportType;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;
import com.github.whitepin.sdk.whitepin.vo.TradeVo;
import com.github.whitepin.sdk.whitepin.vo.UserVo;
import com.github.whitepin.server.api.component.ListTradeVoToEvaluationListDTO;
import com.github.whitepin.server.api.dto.EvaluationAverageDTO;
import com.github.whitepin.server.api.dto.EvaluationListDTO;
import com.github.whitepin.server.api.dto.UserDTO;
import com.github.whitepin.server.api.entity.UserEntity;
import com.github.whitepin.server.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter<UserEntity, UserDTO> userEntityUserDTOConverter;

    @Autowired
    private ListTradeVoToEvaluationListDTO listTradeVoEvaluationListDTOConverter;

    @Autowired
    private FabricContruct fabricContruct;

    @Autowired
    private ChaincodeInvocation chaincodeInvocation;

    public EvaluationAverageDTO getUserEvaluationAverage(String userToken) throws Exception {
        UserVo totalUserVo = chaincodeInvocation.queryTotalUser(fabricContruct.getChannel(), fabricContruct.getClient());
        UserVo userVo = chaincodeInvocation.queryUser(fabricContruct.getChannel(), fabricContruct.getClient(), userToken);
        return EvaluationAverageDTO.builder()
                .userToken(userToken)
                .evaluationBuy(
                        EvaluationAverageDTO.Average.builder()
                                .division("1")
                                .A(scoreConverter(userVo.getBuyAvg().getEvalAvg1()))
                                .B(scoreConverter(userVo.getBuyAvg().getEvalAvg1()))
                                .C(scoreConverter(userVo.getBuyAvg().getEvalAvg1()))
                                .totalAverage(scoreConverter(userVo.getBuyAvg().getTotAvg()))
                                .build())
                .evaluationSell(
                        EvaluationAverageDTO.Average.builder()
                                .division("2")
                                .A(scoreConverter(userVo.getSellAvg().getEvalAvg1()))
                                .B(scoreConverter(userVo.getSellAvg().getEvalAvg2()))
                                .C(scoreConverter(userVo.getSellAvg().getEvalAvg3()))
                                .totalAverage(scoreConverter(userVo.getSellAvg().getTotAvg()))
                                .build())
                .evaluationSum(
                        EvaluationAverageDTO.Average.builder()
                                .division("3")
                                .A(scoreConverter(userVo.getTradeAvg().getEvalAvg1()))
                                .B(scoreConverter(userVo.getTradeAvg().getEvalAvg2()))
                                .C(scoreConverter(userVo.getTradeAvg().getEvalAvg3()))
                                .totalAverage(scoreConverter(userVo.getTradeAvg().getTotAvg()))
                                .build())
                .evaluationWhitepin(
                        EvaluationAverageDTO.Average.builder()
                                .division("4")
                                .A(scoreConverter(totalUserVo.getTradeAvg().getEvalAvg1()))
                                .B(scoreConverter(totalUserVo.getTradeAvg().getEvalAvg2()))
                                .C(scoreConverter(totalUserVo.getTradeAvg().getEvalAvg3()))
                                .totalAverage(scoreConverter(totalUserVo.getTradeAvg().getTotAvg()))
                                .build())
                .build();
    }

    public EvaluationListDTO getMyEvaluationList(String filterDivision, String pageNumber, String orderType, boolean isEvaluationReceived) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userEntityUserDTOConverter.convert(userRepository.findByEmail(userDetails.getUsername()));

        return getUserEvaluationList(userDTO.getUserToken(), filterDivision, pageNumber, orderType, isEvaluationReceived);
    }

    public EvaluationListDTO getUserEvaluationList(String userToken, String filterDivision, String pageNumber, String orderType, boolean isEvaluationReceived) throws Exception {
        String pagingSize = "10";
        List<TradeVo> tradeVo = chaincodeInvocation.queryTradeWithUser(
                fabricContruct.getChannel()
                , fabricContruct.getClient()
                , userToken
                , "buy".equals(filterDivision) ? ConditionType.BUY : ConditionType.SELL
                , "desc".equals(orderType) ? OrderType.DESC : OrderType.ASC
                , ReportType.PAGE
                , pagingSize
                , pageNumber
                , ""
        );

        UserVo userVo = chaincodeInvocation.queryUser(fabricContruct.getChannel(), fabricContruct.getClient(), userToken);

        EvaluationListDTO evaluationListDTO = listTradeVoEvaluationListDTOConverter.convert(filterDivision, tradeVo, isEvaluationReceived);
        evaluationListDTO.setUserToken(userToken);
        evaluationListDTO.setFilterDivision(filterDivision);
        evaluationListDTO.setPagingSize(pagingSize);
        evaluationListDTO.setPageNumber(pageNumber);
        evaluationListDTO.setOrderType(orderType);
        evaluationListDTO.setTotalCount("buy".equals(filterDivision) ? userVo.getBuyAmt(): userVo.getSellAmt());

        return evaluationListDTO;
    }


    private String scoreConverter(double score){
        return new BigDecimal(score).setScale(1, RoundingMode.DOWN).toString();
    }
}
