package com.ubs.sis.admin.controller.v1.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.commons.dto.response.Response;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.sis.admin.auth.annotation.AssignGrant;
import com.ubs.sis.admin.auth.client.KeycloakAuthClient;
import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.auth.dto.filter.UserFilterDto;
import com.ubs.sis.admin.auth.dto.request.UserRequestDto;
import com.ubs.sis.admin.auth.dto.response.UserResponseDto;
import com.ubs.sis.admin.auth.policy.Actions;
import com.ubs.sis.admin.auth.policy.Components;
import com.ubs.sis.admin.auth.service.UserService;
import com.ubs.sis.admin.auth.service.facade.UserServiceFacade;
import com.ubs.sis.admin.master.dto.response.ContextMenuResponse;
import com.ubs.sis.admin.master.service.ContextMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/users")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserServiceFacade userServiceFacade;
    private final KeycloakAuthClient keycloakAuthClient;

    public UserController(UserService userService, UserServiceFacade userServiceFacade,
                          KeycloakAuthClient keycloakAuthClient) {
        this.userService = userService;
        this.userServiceFacade = userServiceFacade;
        this.keycloakAuthClient = keycloakAuthClient;
    }

    @AssignGrant(component = Components.USER, action = Actions.VIEW_LIST)
    @GetMapping
    public Response<List<UserResponseDto>> getList(UserFilterDto filterDto) {
        return userService.getList(filterDto);
    }

    @AssignGrant(component = Components.USER, action = Actions.VIEW_ONE)
    @GetMapping("/username")
    public Response<String> getUsername() {
        Response<String> response = Response.ok();
        response.setPayload(keycloakAuthClient.getCurrentUsername());
        return response;
    }

    @AssignGrant(component = Components.USER, action = Actions.VIEW_ONE)
    @GetMapping("/{username}")
    public Response<String> queryUsername(@PathVariable String username) {
        Response<String> response = Response.ok();

        if (userService.isUserExist(username))
            response
                    .setStatusCode(GearsResponseStatus.DUPLICATED_ENTRY.getBusinessStatusCode())
                    .setMessage("Username already exists.");
        else
            response
                    .setStatusCode(GearsResponseStatus.NOT_FOUND.getBusinessStatusCode())
                    .setMessage("Username doesn't exist.");

        return response;
    }

    @AssignGrant(component = Components.USER, action = Actions.CREATE)
    @PostMapping("/create")
    public Response<String> createUser(@RequestBody @Valid UserRequestDto userRequestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("UserController:createUser - request received, request body: " + objectMapper.writeValueAsString(userRequestDto));

        Response<String> response = Response.ok();

        if (userService.isUserExist(userRequestDto.getUsername())) {
            response.setStatusCode(GearsResponseStatus.DUPLICATED_ENTRY.getBusinessStatusCode());
            response.setMessage("Username " + userRequestDto.getUsername() + " already exists.");
            return response;
        }

        if (userService.isEmailExist(userRequestDto.getEmail())) {
            response.setStatusCode(GearsResponseStatus.DUPLICATED_ENTRY.getBusinessStatusCode());
            response.setMessage("Email already exists.");
            return response;
        }

        return response.setMessage(userServiceFacade.createUser(userRequestDto))
                .setStatusCode(201);
    }

    @AssignGrant(component = Components.USER, action = Actions.UPDATE)
    @PostMapping("/update")
    public Response<String> updateUser(@RequestBody @Valid UserRequestDto userRequestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("UserController:updateUser - request received, request body: " + objectMapper.writeValueAsString(userRequestDto));

        Response<String> response = Response.ok();

        if (!userService.isUserExist(userRequestDto.getUsername())) {
            response.setStatusCode(GearsResponseStatus.NOT_FOUND.getBusinessStatusCode());
            response.setMessage("Username " + userRequestDto.getUsername() + " does not exists.");
            return response;
        }

        User updatingUser = userService.getEntityByUsername(userRequestDto.getUsername());
        User updatedUser = userService.validateAndMapToEntityForUpdate(userRequestDto, updatingUser);

        return response.setMessage(userServiceFacade.updateUser(updatedUser))
                .setStatusCode(200);
    }

    @AssignGrant(component = Components.USER, action = Actions.DELETE)
    @DeleteMapping("/delete/{username}")
    public Response<String> deleteUser(@PathVariable String username) {
        log.info("UserController:deleteUser - request received, username: " + username);
        Response<String> response = Response.ok();

        if (!userService.isUserExist(username))
            return response
                    .setStatusCode(GearsResponseStatus.NOT_FOUND.getBusinessStatusCode())
                    .setMessage("Username " + username + " does not exist.");

        User user = userService.getEntityByUsername(username);
        userServiceFacade.deleteUser(user);
        return response.setStatusCode(200).setMessage("User " + username + " successfully deleted.");
    }

    @AssignGrant(component = Components.USER, action = Actions.VIEW_ONE)
    @GetMapping("/context-menu")
    public Response<ContextMenuResponse> getContextMenu() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "ssadmin";
        //TODO set user into auth principle
        log.info("UserController:getContextMenu - request received, username: " + username);

        User user = userService.getEntityByUsername(username);
        return null;
    }
}
