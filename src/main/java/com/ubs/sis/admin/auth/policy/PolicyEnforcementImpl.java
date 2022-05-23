package com.ubs.sis.admin.auth.policy;

import com.ubs.sis.admin.auth.client.KeycloakAuthClient;
import com.ubs.sis.admin.auth.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class PolicyEnforcementImpl implements PolicyEnforcement {

    private final KeycloakAuthClient keycloakAuthClient;

    public PolicyEnforcementImpl(KeycloakAuthClient keycloakAuthClient) {
        this.keycloakAuthClient = keycloakAuthClient;
    }

    @Override
    public boolean check(String component, String action) {
        User user = keycloakAuthClient.getCurrentUser();

        if (user == null || user.getRole() == null || user.getRole().getGrants() == null) {
            throw new AccessDeniedException("Unauthorized request.", null);
        }

        user.getRole().getGrants().stream()
                .filter(grantObj -> grantObj.getComponent().getCode().equals(component) && grantObj.getAction().getCode().equals(action))
                .findAny().orElseThrow(() -> new AccessDeniedException("Unauthorized request.", null));

        return true;
    }
}
