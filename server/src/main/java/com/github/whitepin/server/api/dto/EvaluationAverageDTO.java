package com.github.whitepin.server.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@XmlRootElement(name="EvaluationAverage")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "구매/판매 평가")
public class EvaluationAverageDTO {

    @ApiModelProperty(notes = "사용자 토큰")
    String userToken;

    @ApiModelProperty(notes = "구매 점수")
    Average evaluationBuy;

    @ApiModelProperty(notes = "판매 점수")
    Average evaluationSell;

    @ApiModelProperty(notes = "합계 점수")
    Average evaluationSum;

    @ApiModelProperty(notes = "화이트핀 전체 평균")
    Average evaluationWhitepin;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    @ApiModel(description = "편균")
    public static class Average {

        @ApiModelProperty(notes = "구분 : 1(구매), 2(판매), 3(합계), 4(화이트핀 전체)")
        String division = "";

        @ApiModelProperty(notes = "A 항목")
        String A = "";

        @ApiModelProperty(notes = "B 항목")
        String B = "";

        @ApiModelProperty(notes = "C 항목")
        String C = "";

        @ApiModelProperty(notes = "전체 평균")
        String totalAverage = "";
    }
}
