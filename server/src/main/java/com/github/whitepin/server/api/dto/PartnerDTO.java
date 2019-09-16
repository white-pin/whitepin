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
@XmlRootElement(name="partner")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "파트너")
public class PartnerDTO {

    @ApiModelProperty(notes = "파트너 코드")
    String code;

    @ApiModelProperty(notes = "파트너 명")
    String name;

}
