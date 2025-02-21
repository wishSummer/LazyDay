package io.github.wishsummer.api.model;

import io.github.wishsummer.api.domain.SysMenuObject;
import io.github.wishsummer.api.domain.SysRoleObject;
import io.github.wishsummer.api.domain.SysUserObject;
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
