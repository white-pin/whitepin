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
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "회원")
public class UserDTO {

    @ApiModelProperty(notes = "이름")
    String name;

    @ApiModelProperty(notes = "이메일")
    String email;

    @ApiModelProperty(notes = "휴대폰번호")
    String phoneNumber;

    @ApiModelProperty(notes = "사용자 토큰")
    String userToken;

    @ApiModelProperty(notes = "연결 된 서비스 X건")
    int connectedServiceCount = 0;

    @ApiModelProperty(notes = "거래내역 X건")
    int transactionCount = 0;

    @ApiModelProperty(notes = "내가 받은 평가 X건")
    int evaluationReceivedCount = 0;

    @ApiModelProperty(notes = "내가 남긴 평가 X건")
    int myLeftEvaluationCount = 0;

    @ApiModelProperty(notes = "생성 일시")
    java.util.Calendar createDate;


}
