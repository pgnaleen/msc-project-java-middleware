package com.ubs.sis.admin.auth.config;

import org.keycloak.KeycloakPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            return Optional.ofNullable(((KeycloakPrincipal) auth.getPrincipal())
                    .getKeycloakSecurityContext()
                    .getToken()
                    .getPreferredUsername());

//             with Principal get only keycloak id not username ex. b5f7a893-7a0d-46d0-9b3d-55dd7307bdcb
//			return ((Principal) this.auth.getPrincipal()).getName();
        }

        return null;
    }
}
