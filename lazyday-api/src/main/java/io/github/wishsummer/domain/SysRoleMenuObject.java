package io.github.wishsummer.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 角色和菜单关联表
* @TableName sys_role_menu
*/
public class SysRoleMenuObject implements Serializable {

    /**
    * 角色ID
    */
    @NotNull(message="[角色ID]不能为空")
    @ApiModelProperty("角色ID")
    private Long roleId;
    /**
    * 菜单ID
    */
    @NotNull(message="[菜单ID]不能为空")
    @ApiModelProperty("菜单ID")
    private Long menuId;

    /**
    * 角色ID
    */
    private void setRoleId(Long roleId){
    this.roleId = roleId;
    }

    /**
    * 菜单ID
    */
    private void setMenuId(Long menuId){
    this.menuId = menuId;
    }


    /**
    * 角色ID
    */
    private Long getRoleId(){
    return this.roleId;
    }

    /**
    * 菜单ID
    */
    private Long getMenuId(){
    return this.menuId;
    }

}
