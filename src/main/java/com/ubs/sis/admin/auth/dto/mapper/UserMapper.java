package com.ubs.sis.admin.auth.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.auth.dto.request.UserRequestDto;
import com.ubs.sis.admin.auth.dto.response.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserRequestDto, UserResponseDto> {
}
