package com.mantis.tech.polls.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotBlank(message = "Username or email address should not be empty.")
    @NotNull(message = "Username or email address should not be null.")
    private String userNameOrEmail;
    @NotBlank(message = "Password should not be empty.")
    @NotNull(message = "Password should not be null.")
    private String password;

    public String getUserNameOrEmail() {
        return userNameOrEmail;
    }

    public void setUserNameOrEmail(String userNameOrEmail) {
        this.userNameOrEmail = userNameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
