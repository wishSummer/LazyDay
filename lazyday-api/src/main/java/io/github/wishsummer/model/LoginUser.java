package io.github.wishsummer.model;

import io.github.wishsummer.domain.SysMenuObject;
import io.github.wishsummer.domain.SysRoleObject;
import io.github.wishsummer.domain.SysUserObject;
import lombok.Data;

import java.util.List;

@Data
public class LoginUser {

    private String token;

    private Long loginTime;

    private List<SysRoleObject> roleObjectList;

    private List<SysMenuObject> sysMenuObjects;

    private SysUserObject sysUserObject;

    private Long expireTime;

}
