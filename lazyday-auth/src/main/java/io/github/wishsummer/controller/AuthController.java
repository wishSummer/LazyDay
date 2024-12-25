package io.github.wishsummer.controller;

import io.github.wishsummer.controller.form.LoginForm;
import io.github.wishsummer.controller.form.RegisterForm;
import io.github.wishsummer.domain.Result;
import io.github.wishsummer.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class AuthController {

    private AuthService authService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginForm form) {
        return Result.success(authService.login(form.getUsername(), form.getPassword()));
    }

    /**
     * 退出
     */
    @DeleteMapping("logout")
    public Result<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return Result.success();
    }

    /**
     * 刷新登录状态
     */
    @PostMapping("refresh")
    public Result<?> refresh(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public Result<?> register(@RequestBody @Validated RegisterForm registerBody) {
        return authService.register(registerBody);
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
