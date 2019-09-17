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
@XmlRootElement(name="count")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "누적 사용자/거래 수")
public class CountDTO {

    @ApiModelProperty(notes = "사용자")
    int user = 0;

    @ApiModelProperty(notes = "거래")
    int transaction = 0;

}
