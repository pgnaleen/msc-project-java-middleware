package com.ubs.sis.admin.auth.config;

import com.ubs.sis.admin.auth.client.KeycloakAuthClient;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final KeycloakAuthClient keycloakAuthClient;

    public AuthInterceptor(KeycloakAuthClient keycloakAuthClient) {
        this.keycloakAuthClient = keycloakAuthClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            return true;
        }

        String token = authorization.replaceFirst("Bearer ", "");

        boolean authorized = keycloakAuthClient.introspect(token);

        if (authorized) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
