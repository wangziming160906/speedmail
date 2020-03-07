package com.speedmail.menu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


/**
 * @Description  
 * @Author  wangshuai
 * @Date 2020-02-17 
 */

@Data
@Entity
@Table ( name ="speedmail_menu" )
public class MenuEntity {

	@Id
   	@Column(name = "menuid" )
	private Long id;

	/**
	 * 父id
	 */
   	@Column(name = "pid" )
	private String pid;

	/**
	 * 名称
	 */
   	@Column(name = "title" )
	private String title;

	/**
	 * 菜单图标
	 */
   	@Column(name = "icon" )
	private String icon;

	/**
	 * 链接
	 */
   	@Column(name = "url" )
	private String url;

	/**
	 * 文件路径
	 */
   	@Column(name = "file_path" )
	private String file_path;

	/**
	 * 链接参数
	 */
   	@Column(name = "params" )
	private String params;

	/**
	 * 权限节点
	 */
   	@Column(name = "node" )
	private String node;

	/**
	 * 菜单排序
	 */
   	@Column(name = "sort" )
	private Long sort;

	/**
	 * 状态(0:禁用,1:启用)
	 */
   	@Column(name = "status" )
	private Integer status;

	/**
	 * 创建人
	 */
   	@Column(name = "create_by" )
	private Long create_by;

	/**
	 * 创建时间
	 */
   	@Column(name = "create_at" )
	private String create_at;

	/**
	 * 是否内页
	 */
   	@Column(name = "is_inner" )
	private String inner;

	/**
	 * 参数默认值
	 */
   	@Column(name = "values" )
	private String values;

	/**
	 * 是否显示侧栏
	 */
   	@Column(name = "show_slider" )
	private Integer show_slider;

}
