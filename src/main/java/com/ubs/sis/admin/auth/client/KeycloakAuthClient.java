package com.ubs.sis.admin.auth.client;

import com.ubs.commons.exception.GearsException;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.auth.dto.response.UserResponseDto;
import com.ubs.sis.admin.auth.model.KeycloakAuthResponse;
import com.ubs.sis.admin.auth.model.LogoutRequest;
import com.ubs.sis.admin.auth.model.TokenRequest;
import com.ubs.sis.admin.auth.service.UserService;
import lombok.Getter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@Getter
public class KeycloakAuthClient {
    private static final Logger log = LoggerFactory.getLogger(KeycloakAuthClient.class);

    @Value("${keycloak-auth-url}")
    private String authUrl;

    @Value("${keycloak.auth-server-url}")
    private String baseAuthUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${master_realm_admin_username}")
    private String username;

    @Value("${master_realm_admin_password}")
    private String password;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;
    private final UserService userService;
    private Keycloak keycloak = null;

    public KeycloakAuthClient(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public KeycloakAuthResponse authenticate(TokenRequest tokenRequest) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("grant_type", "authorization_code");
        paramMap.add("code", tokenRequest.getCode());
        paramMap.add("client_id", clientId);
        paramMap.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(paramMap, headers);

        ResponseEntity<KeycloakAuthResponse> response;
        try {
            response = restTemplate.exchange(authUrl + "//token", HttpMethod.POST, entity, KeycloakAuthResponse.class);
        } catch (HttpClientErrorException e) {
            throw new GearsException(GearsResponseStatus.INVALID_PARAMS, e.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new GearsException(GearsResponseStatus.INTERNAL_ERROR, exception.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error(response.getStatusCode() + ": Failed to authenticate");
            throw new GearsException(GearsResponseStatus.INTERNAL_ERROR, response.getStatusCode() + ": Failed to authenticate");
        }

        log.info("Authentication success");

        return response.getBody();
    }

    public void logout(LogoutRequest logoutRequest) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("refresh_token", logoutRequest.getRefreshToken());
        paramMap.add("client_id", clientId);
        paramMap.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(paramMap, headers);

        ResponseEntity<KeycloakAuthResponse> response;
        try {
            response = restTemplate.exchange(authUrl + "//logout", HttpMethod.POST, entity, KeycloakAuthResponse.class);
        } catch (HttpClientErrorException e) {
            throw new GearsException(GearsResponseStatus.INVALID_PARAMS, e.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new GearsException(GearsResponseStatus.INTERNAL_ERROR, exception.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error(response.getStatusCode() + ": Failed to logout");
            throw new GearsException(GearsResponseStatus.INTERNAL_ERROR, response.getStatusCode() + ": Failed to logout");
        }

        log.info("Logout success");
    }

    public boolean introspect(String token) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", clientId);
        paramMap.add("client_secret", clientSecret);
        paramMap.add("token", token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(paramMap, headers);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.exchange(authUrl + "//token//introspect", HttpMethod.POST, entity, Map.class);
        } catch (HttpClientErrorException e) {
            throw new GearsException(GearsResponseStatus.INVALID_PARAMS, e.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new GearsException(GearsResponseStatus.INTERNAL_ERROR, exception.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error(response.getStatusCode() + ": Failed to introspect");
            throw new GearsException(GearsResponseStatus.INTERNAL_ERROR,response.getStatusCode() + ": Failed to introspect");
        }

        log.info("Introspect success");

        return (Boolean) response.getBody().get("active");
    }

    public User getCurrentUser() {
        return userService.getEntityByUsername(getCurrentUsername());
    }

    public UserResponseDto getCurrentUserDto() {
        return userService.getDtoByUsername(getCurrentUsername());
    }

    public String getCurrentUsername() {
        return ((SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication()
                .getDetails()).getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    public Keycloak getKeycloakInstance() {
        try {
            if (keycloak == null) {
                keycloak = KeycloakBuilder.builder()
                        .serverUrl(baseAuthUrl)
                        .realm("master")
                        .grantType(OAuth2Constants.PASSWORD)
                        .username(username)
                        .password(password)
                        .clientId("admin-cli")
                        .resteasyClient(new ResteasyClientBuilder()
                                .connectionPoolSize(2)
                                .build())
                        .build();// there was issue with not release connections from resteasy client.
                // RCA was not using version of keycloak admin client and corresponding resteasy client
            }
        } catch (IllegalStateException iex) {
            log.info("keycloak client not created", iex);
            return keycloak;
        }

        return keycloak;
    }
}