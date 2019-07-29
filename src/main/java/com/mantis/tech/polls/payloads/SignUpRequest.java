package com.mantis.tech.polls.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpRequest {

    @NotNull(message = "Name should not be Null.")
    @NotBlank(message = "Name should not be empty.")
    @Size(min = 3)
    private String name;
    @Size(min = 3)
    @NotNull(message = "Username should not be Null.")
    @NotBlank(message = "Username should not be empty.")
    private String userName;

    @NotNull(message = "Password should not be Null.")
    @NotBlank(message = "Password should not be empty.")
    private String password;
    @NotNull(message = "Email address should not be Null.")
    @NotBlank(message = "Email address should not be empty.")
    @Email
    private String emailAddress;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
