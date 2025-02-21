package io.github.wishsummer.controller;

import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.api.model.LoginUser;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.common.core.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class SysUserController {

    private SysUserService sysUserService;

    @GetMapping("/{userId}")
    public Result<LoginUser> login(@PathVariable("userId") String userId) {
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody SysUserObject userObjectt){
        return sysUserService.register(userObjectt);
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
