package com.github.whitepin.server.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@XmlRootElement(name="join")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "회원 가입")
public class JoinDTO {

    @NotNull
    @NotBlank
    @ApiModelProperty(notes = "이름")
    String name;

    @Email()
    @ApiModelProperty(notes = "이메일")
    String email;

    @ApiModelProperty(notes = "비밀번호")
    String password;

    @ApiModelProperty(notes = "비밀번호 확인")
    String rePassword;

    @ApiModelProperty(notes = "휴대폰번호")
    String phoneNumber;

    @ApiModelProperty(notes = "ci")
    String ci;

    @ApiModelProperty(notes = "di")
    String di;
}
