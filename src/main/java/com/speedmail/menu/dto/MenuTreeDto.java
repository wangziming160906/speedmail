package com.speedmail.menu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class MenuTreeDto {

    private Long id;
    /**
     * 父id
     */
    private String pid;

    /**
     * 名称
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 链接
     */
    private String url;

    /**
     * 文件路径
     */
    private String file_path;

    /**
     * 链接参数
     */
    private String params;

    /**
     * 权限节点
     */
    private String node;

    /**
     * 菜单排序
     */
    private Long sort;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long create_by;

    /**
     * 创建时间
     */
    private String create_at;

    /**
     * 是否内页
     */
    private boolean is_inner;

    /**
     * 参数默认值
     */
    private String values;

    /**
     * 是否显示侧栏
     */
    private Integer show_slider;

    private String fullUrl;

    private List<MenuTreeDto> children;

    public boolean isIs_inner() {
        return is_inner;
    }

    public void setIs_inner(boolean is_inner) {
        this.is_inner = is_inner;
    }
}
