package io.github.wishsummer.controller;

import io.github.wishsummer.domain.Result;
import io.github.wishsummer.model.LoginUser;
import io.github.wishsummer.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class SysUserController {

    private SysUserService sysUserService;

    @GetMapping("/{userId}")
    public Result<LoginUser> login(@PathVariable("userId") String userId) {
        return Result.success();
    }


    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
