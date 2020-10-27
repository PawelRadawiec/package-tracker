package com.info.packagetrackerbackend.model.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty(message = "must be set")
    private String username;

    @NotEmpty(message = "must be set")
    private String password;
}
