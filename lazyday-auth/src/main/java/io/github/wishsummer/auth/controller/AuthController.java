package io.github.wishsummer.auth.controller;

import io.github.wishsummer.auth.controller.form.LoginForm;
import io.github.wishsummer.auth.controller.form.RegisterForm;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.auth.service.AuthService;
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
        return authService.logout(request);
    }

    /**
     * 刷新登录状态
     */
    @PostMapping("refresh")
    public Result<String> refresh(HttpServletRequest request) {
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
