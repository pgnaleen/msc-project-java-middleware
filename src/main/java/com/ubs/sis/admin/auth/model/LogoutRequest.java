package com.ubs.sis.admin.auth.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LogoutRequest {

    @NotNull(message = "Refresh token is required")
    private String refreshToken;
}
