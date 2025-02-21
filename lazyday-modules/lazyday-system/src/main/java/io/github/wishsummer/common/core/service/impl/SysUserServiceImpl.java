package io.github.wishsummer.common.core.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.api.domain.SysMenuObject;
import io.github.wishsummer.api.domain.SysRoleObject;
import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.common.core.service.SysUserService;
import io.github.wishsummer.mapper.SysRoleMenuMapper;
import io.github.wishsummer.mapper.SysUserMapper;
import io.github.wishsummer.mapper.SysUserRoleMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserObject> implements IService<SysUserObject>, SysUserService {

    private SysRoleMenuMapper sysRoleMenuMapper;

    private SysUserRoleMapper sysUserRoleMapper;

    private SysUserMapper sysUserMapper;


    @Override
    public Result<LoginUser> getUserInfo(String username) {
        SysUserObject sysUserObject = this.getBaseMapper().selectByUsername(username);
        if (ObjectUtils.isEmpty(sysUserObject)) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUserObject(sysUserObject);
        List<SysRoleObject> sysRoleObjects = sysUserRoleMapper.selectSysUserRoleObject(sysUserObject.getUserId());
        List<SysMenuObject> sysMenuObjects = sysRoleMenuMapper.selectMenusByRoleId(sysRoleObjects.stream().map(SysRoleObject::getRoleId).collect(Collectors.toList()));
        loginUser.setRoleObjectList(sysRoleObjects);
        loginUser.setSysMenuObjects(sysMenuObjects);
        return Result.success(loginUser);
    }

    @Override
    public Result register(SysUserObject userObjectt) {
        int insert = sysUserMapper.insert(userObjectt);
        if (insert > 0) {
            return Result.success();
        }
        return Result.error();
    }

    @Autowired
    public void setSysRoleMenuMapper(SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    @Autowired
    public void setSysUserRoleMapper(SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }
}
