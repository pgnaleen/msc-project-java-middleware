package com.ubs.sis.admin.controller.v1.api;

import com.ubs.commons.dto.response.Response;
import com.ubs.sis.admin.auth.client.KeycloakAuthClient;
import com.ubs.sis.admin.auth.dto.response.UserResponseDto;
import com.ubs.sis.admin.auth.model.KeycloakAuthResponse;
import com.ubs.sis.admin.auth.model.LogoutRequest;
import com.ubs.sis.admin.auth.model.TokenRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KeycloakAuthClient keycloakAuthClient;

    public AuthController(KeycloakAuthClient keycloakAuthClient) {
        this.keycloakAuthClient = keycloakAuthClient;
    }

    @PostMapping(path = "/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Response<KeycloakAuthResponse> getToken(TokenRequest tokenRequest) {
        KeycloakAuthResponse keycloakAuthResponse = keycloakAuthClient.authenticate(tokenRequest);
        Response<KeycloakAuthResponse> response = Response.ok();
        return response.setPayload(keycloakAuthResponse);
    }

    @PostMapping(path = "/logout", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Response<?> logout(LogoutRequest logoutRequest) {
        keycloakAuthClient.logout(logoutRequest);
        return Response.ok().setMessage("Logout success");
    }

    @GetMapping("/current-user")
    public Response<UserResponseDto> getCurrentUser() {
        Response<UserResponseDto> response = Response.ok();
        return response.setPayload(keycloakAuthClient.getCurrentUserDto());
    }
}
