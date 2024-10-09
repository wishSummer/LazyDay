package io.github.wishsummer.domain;


import java.io.Serializable;

import java.util.Date;


/**
 * 角色信息表
 *
 * @TableName sys_role
 */
public class SysRoleObject implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色英文名称
     */
    private String roleKey;
    /**
     * 显示顺序
     */
    private Integer roleSort;
    /**
     * 菜单树选择项是否关联显示
     */
    private Integer menuCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

}
