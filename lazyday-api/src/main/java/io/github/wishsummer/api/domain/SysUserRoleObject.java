package io.github.wishsummer.api.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户和角色关联表
 *
 * @TableName sys_user_role
 */
@TableName(value = "sys_user_role")
@Data
public class SysUserRoleObject implements Serializable {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 角色ID
     */
    private Integer roleId;

}
