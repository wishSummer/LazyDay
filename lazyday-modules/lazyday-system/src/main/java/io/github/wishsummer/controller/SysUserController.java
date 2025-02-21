package io.github.wishsummer.controller;

import io.github.wishsummer.domain.Result;
import io.github.wishsummer.domain.SysUserObject;
import io.github.wishsummer.model.LoginUser;
import io.github.wishsummer.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class SysUserController {

    private SysUserService sysUserService;

    @GetMapping("/info/{userId}")
    public Result<LoginUser> login(@PathVariable("userId") String userId) {
        return sysUserService.getUser(userId);
    }

    @GetMapping("/getInfo")
    public Result<Map<String, Object>> getInfo() {
        return sysUserService.getUserInfo();
    }

    @PostMapping("/register")
    public Result register(@RequestBody SysUserObject userObjectt) {
        return sysUserService.register(userObjectt);
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
