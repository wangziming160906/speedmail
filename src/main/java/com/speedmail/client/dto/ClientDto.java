package com.speedmail.client.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ClientDto implements Serializable {

    private static final long serialVersionUID =  4618746828148619040L;

    /**
     * 系统前台用户表
     */
    private Long id;

    /**
     * 用户登陆账号
     */
    private String account;

    /**
     * 登陆密码，32位加密串
     */
    private String password;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private String lastLoginTime;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 身份证
     */
    private String idcard;

    /**
     * 省
     */
    private Long province;

    /**
     * 市
     */
    private Long city;

    /**
     * 区
     */
    private Long area;

    /**
     * 所在地址
     */
    private String address;

    /**
     * 备注
     */
    private String description;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 编号
     */
    private String code;

    /**
     * 钉钉openid
     */
    private String dingtalkOpenid;

    /**
     * 钉钉unionid
     */
    private String dingtalkUnionid;

    /**
     * 钉钉用户id
     */
    private String dingtalkUserid;

}
