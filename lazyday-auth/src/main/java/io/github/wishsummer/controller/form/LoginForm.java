package io.github.wishsummer.controller.form;

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
