package com.ubs.sis.admin.auth.service.facade.impl;

import com.ubs.sis.admin.auth.client.KeycloakAuthClient;
import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.auth.dto.request.UserRequestDto;
import com.ubs.sis.admin.auth.service.UserService;
import com.ubs.sis.admin.auth.service.facade.UserServiceFacade;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceFacadeImpl implements UserServiceFacade {
    Logger log = LoggerFactory.getLogger(UserServiceFacadeImpl.class);

    private KeycloakAuthClient authClient;
    private UserService userService;

    public UserServiceFacadeImpl(KeycloakAuthClient keycloakAuthClient, UserService userService) {
        this.authClient = keycloakAuthClient;
        this.userService = userService;
    }

    @Override
    public String createUser(UserRequestDto userRequestDto) {
        User user = userService.validateAndMapToEntityForSave(userRequestDto);
        user.setTenantId(0L);
        userService.getRepository().save(user);

        UsersResource usersResource = authClient.getKeycloakInstance().realm(authClient.getRealm()).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);
        usersResource.create(kcUser);

        return "User successfully created.";
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    @Override
    public String updateUser(User user) {
        // updating database
        user.setTenantId(0L);
        userService.getRepository().save(user);

        // updating keycloak user
        UsersResource usersResource = authClient.getKeycloakInstance().realm(authClient.getRealm()).users();
        List<UserRepresentation> updateUsers = usersResource.search(user.getUsername(), true);

        if (updateUsers.isEmpty()) {
            return "Username doesn't exist in keycloak.";
        }
        if (updateUsers.size() > 1 ) {
            return "More than one users exist for given username in keycloak";
        }

        UserRepresentation updateKeycloakUser = updateUsers.get(0);

        if (user.getPassword() != null) {
            log.info(user.getPassword());
            CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
            updateKeycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
        }
        if (user.getEmail() != null) {
            updateKeycloakUser.setEmail(user.getEmail());
        }
        if (user.getFirstName() != null) {
            updateKeycloakUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            updateKeycloakUser.setLastName(user.getLastName());
        }

        // get UserResource from UsersResource and set update for user
        usersResource.get(updateKeycloakUser.getId()).update(updateKeycloakUser);

        return "User successfully updated.";
    }

    @Override
    public String deleteUser(User user) {
        userService.getRepository().delete(user);

        UsersResource usersResource = authClient.getKeycloakInstance().realm(authClient.getRealm()).users();
        List<UserRepresentation> updateUsers = usersResource.search(user.getUsername(), true);

        if (updateUsers.isEmpty()) {
            return "Username doesn't exist in keycloak.";
        }

        if (updateUsers.size() > 1 ) {
            return "More than one users exist for given username.";
        }

        UserRepresentation updateUser = updateUsers.get(0);
        usersResource.delete(updateUser.getId());

        return "User successfully deleted.";
    }
}
