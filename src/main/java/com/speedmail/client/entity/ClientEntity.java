package com.speedmail.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description  
 * @Author  WANGSHUAI
 * @Date 2020-02-17 
 */

@Data
@Entity
@Table ( name ="speedmail_client" )
public class ClientEntity {

	/**
	 * 系统前台用户表
	 */
	@Id
	@Column(name = "clientid" )
	private Long id;

	/**
	 * 用户登陆账号
	 */
   	@Column(name = "account" )
	private String account;

	/**
	 * 登陆密码，32位加密串
	 */
   	@Column(name = "password" )
	private String password;

	/**
	 * 用户昵称
	 */
   	@Column(name = "name" )
	private String name;

	/**
	 * 手机
	 */
   	@Column(name = "mobile" )
	private String mobile;

	/**
	 * 真实姓名
	 */
   	@Column(name = "realname" )
	private String realname;

	/**
	 * 创建时间
	 */
   	@Column(name = "create_time" )
	private String createTime;

	/**
	 * 状态
	 */
   	@Column(name = "status" )
	private Integer status;

	/**
	 * 上次登录时间
	 */
   	@Column(name = "last_login_time" )
	private String lastLoginTime;

	/**
	 * 性别
	 */
   	@Column(name = "sex" )
	private String sex;

	/**
	 * 头像
	 */
   	@Column(name = "avatar" )
	private String avatar;

	/**
	 * 身份证
	 */
   	@Column(name = "idcard" )
	private String idcard;

	/**
	 * 省
	 */
   	@Column(name = "province" )
	private Long province;

	/**
	 * 市
	 */
   	@Column(name = "city" )
	private Long city;

	/**
	 * 区
	 */
   	@Column(name = "area" )
	private Long area;

	/**
	 * 所在地址
	 */
   	@Column(name = "address" )
	private String address;

	/**
	 * 备注
	 */
   	@Column(name = "description" )
	private String description;

	/**
	 * 邮箱
	 */
   	@Column(name = "email" )
	private String email;

	/**
	 * 编号
	 */
   	@Column(name = "code" )
	private String code;

	/**
	 * 钉钉openid
	 */
   	@Column(name = "dingtalk_openid" )
	private String dingtalkOpenid;

	/**
	 * 钉钉unionid
	 */
   	@Column(name = "dingtalk_unionid" )
	private String dingtalkUnionid;

	/**
	 * 钉钉用户id
	 */
   	@Column(name = "dingtalk_userid" )
	private String dingtalkUserid;

}
