package io.github.wishsummer.auth.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class LoginForm {

    @NonNull
    private String username;

    @NonNull
    private String password;
}
