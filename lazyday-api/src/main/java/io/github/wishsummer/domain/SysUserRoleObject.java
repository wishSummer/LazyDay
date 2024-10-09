package io.github.wishsummer.domain;


import java.io.Serializable;


/**
 * 用户和角色关联表
 *
 * @TableName sys_user_role
 */
public class SysUserRoleObject implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;

}
