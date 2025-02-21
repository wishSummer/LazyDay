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
import io.github.wishsummer.common.core.utils.ServletUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserObject> implements IService<SysUserObject>, SysUserService {

    private SysRoleMenuMapper sysRoleMenuMapper;

    private SysUserRoleMapper sysUserRoleMapper;

    private SysUserMapper sysUserMapper;


    @Override
    public Result<LoginUser> getUser(String username) {
        SysUserObject sysUserObject = sysUserMapper.selectByUsername(username);
        if (ObjectUtils.isEmpty(sysUserObject)) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUserObject(sysUserObject);

        return Result.success(loginUser);
    }

    @Override
    public Result register(SysUserObject userObject) {
        int insert = sysUserMapper.insert(userObject);
        if (insert > 0) {
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据token返回用户信息到前端TODO
     */
    @Override
    public Result<Map<String, Object>> getUserInfo() {

        Map<String, Object> tokenInfo = ServletUtils.getTokenInfo();
        System.out.println(tokenInfo);
//        List<SysRoleObject> sysRoleObjects = sysUserRoleMapper.selectSysUserRoleObject();
//        List<SysMenuObject> sysMenuObjects = sysRoleMenuMapper.selectMenusByRoleId(sysRoleObjects.stream().map(SysRoleObject::getRoleId).toList());
//        loginUser.setRoleObjectList(sysRoleObjects);
//        loginUser.setSysMenuObjects(sysMenuObjects);
        return null;
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
