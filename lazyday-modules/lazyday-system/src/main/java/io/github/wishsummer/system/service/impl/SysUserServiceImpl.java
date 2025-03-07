package io.github.wishsummer.system.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.api.domain.SysMenuObject;
import io.github.wishsummer.api.domain.SysRoleObject;
import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.common.core.constant.CacheConstants;
import io.github.wishsummer.common.core.constant.Constants;
import io.github.wishsummer.common.core.constant.UserConstants;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.common.core.service.RedisService;
import io.github.wishsummer.common.core.utils.ServletUtils;
import io.github.wishsummer.system.domain.vo.UserInfoVo;
import io.github.wishsummer.system.mapper.SysRoleMenuMapper;
import io.github.wishsummer.system.mapper.SysUserMapper;
import io.github.wishsummer.system.mapper.SysUserRoleMapper;
import io.github.wishsummer.system.service.SysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserObject> implements IService<SysUserObject>, SysUserService {

    private SysRoleMenuMapper sysRoleMenuMapper;

    private SysUserRoleMapper sysUserRoleMapper;

    private SysUserMapper sysUserMapper;
    private RedisService redisService;


    @Override
    public Result<LoginUser> login(String username) {
        SysUserObject sysUserObject = sysUserMapper.selectByUsername(username);
        if (ObjectUtils.isEmpty(sysUserObject)) {
            return Result.error("账号或密码错误！");
        }
        List<SysRoleObject> sysRoleObjects = sysUserRoleMapper.selectSysUserRoleObject(sysUserObject.getUserId());
        List<SysMenuObject> sysMenuObjects = sysRoleMenuMapper.selectMenusByRoleId(sysRoleObjects.stream().map(SysRoleObject::getRoleId).collect(Collectors.toList()));

        LoginUser loginUser = new LoginUser();
        loginUser.setSysUserObject(sysUserObject);
        loginUser.setRoleObjectList(sysRoleObjects);
        loginUser.setSysMenuObjects(sysMenuObjects);

        return Result.success(loginUser);
    }


    @Override
    public Result<String> register(SysUserObject userObject) {
        int insert = sysUserMapper.insert(userObject);
        if (insert > 0) {
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据token返回用户信息到前端TODO
     * TODO 当前从redis中获取的值缺少 菜单、角色、部门信息。后续鉴权需完善。
     */
    @Override
    public Result<UserInfoVo> getUserInfo() {
        Map<String, Object> tokenInfo = ServletUtils.getTokenInfo();
        LoginUser cacheObject = redisService.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + tokenInfo.get(UserConstants.USER_KEY));
        return Result.success(new UserInfoVo().build(cacheObject));
    }

    @Override
    public Result<?> editUser(SysUserObject userObject) {

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

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
