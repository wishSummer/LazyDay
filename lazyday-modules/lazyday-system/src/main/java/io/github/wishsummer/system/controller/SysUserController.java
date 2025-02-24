package io.github.wishsummer.system.controller;

import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.system.domain.vo.UserInfoVo;
import io.github.wishsummer.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class SysUserController {

    private SysUserService sysUserService;

    @GetMapping("/info/{username}")
    public Result<LoginUser> login(@PathVariable("username") String userId) {
        return sysUserService.login(userId);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody SysUserObject userObjectt) {
        return sysUserService.register(userObjectt);
    }

    /**
     * 登陆后初始加载用户信息
     */
    @GetMapping("/user-info")
    public Result<UserInfoVo> getUserInfo() {
        return sysUserService.getUserInfo();
    }

    /**
     * 编辑用户
     */
    @PostMapping("/edit")
    public Result<?> editUser(SysUserObject userObject) {
        return sysUserService.editUser(userObject);
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
