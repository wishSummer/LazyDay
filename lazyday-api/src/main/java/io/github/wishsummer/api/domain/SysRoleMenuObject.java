package io.github.wishsummer.api.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色和菜单关联表
 *
 * @TableName sys_role_menu
 */
@TableName(value = "sys_role_menu")
@Data
public class SysRoleMenuObject implements Serializable {

    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 菜单ID
     */
    private Integer menuId;

}
