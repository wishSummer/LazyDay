package io.github.wishsummer.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.domain.Result;
import io.github.wishsummer.domain.SysMenuObject;
import io.github.wishsummer.domain.SysRoleObject;
import io.github.wishsummer.domain.SysUserObject;
import io.github.wishsummer.mapper.SysRoleMenuMapper;
import io.github.wishsummer.mapper.SysUserMapper;
import io.github.wishsummer.mapper.SysUserRoleMapper;
import io.github.wishsummer.model.LoginUser;
import io.github.wishsummer.service.SysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserObject>
        implements IService<SysUserObject>, SysUserService {

    private SysRoleMenuMapper sysRoleMenuMapper;

    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public Result<LoginUser> getUserInfo(String username) {
        SysUserObject sysUserObject = this.getBaseMapper().selectByUsername(username);
        if (ObjectUtils.isEmpty(sysUserObject)) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUserObject(sysUserObject);
        List<SysRoleObject> sysRoleObjects = sysUserRoleMapper.selectSysUserRoleObject(sysUserObject.getUserId());
        List<SysMenuObject> sysMenuObjects = sysRoleMenuMapper.selectMenusByRoleId(sysRoleObjects.stream().map(SysRoleObject::getRoleId).toList());
        loginUser.setRoleObjectList(sysRoleObjects);
        loginUser.setSysMenuObjects(sysMenuObjects);
        return Result.success(loginUser);
    }

    @Autowired
    public void setSysRoleMenuMapper(SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    @Autowired
    public void setSysUserRoleMapper(SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRoleMapper = sysUserRoleMapper;
    }
}
