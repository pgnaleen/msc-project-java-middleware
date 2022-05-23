package com.ubs.sis.admin.auth.service;

import com.ubs.commons.dto.filter.BaseFilterDto;
import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.auth.dto.request.UserRequestDto;
import com.ubs.sis.admin.auth.dto.response.UserResponseDto;

public interface UserService extends BaseService<User, UserRequestDto, UserResponseDto, BaseFilterDto> {

    User getEntityByUsername(String username);

    UserResponseDto getDtoByUsername(String username);

    boolean isUserExist(String username);

    boolean isEmailExist(String email);
}
