package io.github.controller;

import io.github.form.LoginForm;
import io.github.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm  form) {
        authService.login(form.getUsername(),form.getPassword());
        return null;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
