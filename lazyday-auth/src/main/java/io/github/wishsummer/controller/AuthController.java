package io.github.wishsummer.controller;

import io.github.wishsummer.controller.form.LoginForm;
import io.github.wishsummer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm form) {
        authService.login(form.getUsername(),form.getPassword());
        // TODO 创建token
        return null;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
