package com.github.whitepin.server.api.mock.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@XmlRootElement(name="identification")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "identification")
public class MockIdentificationDTO {

    @NotNull
    @NotBlank
    @ApiModelProperty(notes = "휴대폰번호")
    String phoneNumber;

    @ApiModelProperty(notes = "ci")
    String ci;

    @ApiModelProperty(notes = "di")
    String di;

}
