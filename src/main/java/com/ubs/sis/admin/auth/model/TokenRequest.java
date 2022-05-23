package com.ubs.sis.admin.auth.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TokenRequest {
    private String sessionState;

    @NotNull(message = "Code is required")
    private String code;
}
