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
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "user")
public class UserDTO {

    @ApiModelProperty(notes = "user name")
    String name;

    @ApiModelProperty(notes = "user email")
    String email;

    @ApiModelProperty(notes = "user phoneNumber")
    String phoneNumber;

    @ApiModelProperty(notes = "user userToken")
    String userToken;

}
