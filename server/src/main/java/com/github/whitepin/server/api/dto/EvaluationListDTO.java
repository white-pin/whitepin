package com.github.whitepin.server.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@XmlRootElement(name="EvaluationList")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "구매/판매 평가 목록")
public class EvaluationListDTO {

    @ApiModelProperty(notes = "조회 대상 사용자 토큰")
    String userToken;

    @ApiModelProperty(notes = "조회 구분 : 1(구매), 2(판매), 3(전체)")
    String filterDivision;

    @ApiModelProperty(notes = "페이징 사이즈(한 페이지 표시 갯수)")
    String pagingSize;

    @ApiModelProperty(notes = "조회 페이지 번호")
    String pageNumber;

    @ApiModelProperty(notes = "구매/판매 목록")
    List<Transaction> transaction;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    @ApiModel(description = "구매/판매 목록")
    public static class Transaction {

        @ApiModelProperty(notes = "구분 : 1(구매), 2(판매)")
        String division = "";

        @ApiModelProperty(notes = "거래내역")
        String transactionHash = "";

        @ApiModelProperty(notes = "거래상대")
        String targetUserToken = "";

        @ApiModelProperty(notes = "A 항목")
        String A = "";

        @ApiModelProperty(notes = "B 항목")
        String B = "";

        @ApiModelProperty(notes = "C 항목")
        String C = "";

        @ApiModelProperty(notes = "평가날짜")
        String evaluationDate = "";

        @ApiModelProperty(notes = "서비스 코드")
        String partnerCode = "";

    }
}
