package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 分销商注册参数
 * Created by macro on 2018/4/26.
 */
@Getter
@Setter
public class UmsMemberParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "地址")
    private String city;
    @ApiModelProperty(value = "性别")
    private String gender;

}
