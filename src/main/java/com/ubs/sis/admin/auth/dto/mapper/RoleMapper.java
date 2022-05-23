package com.ubs.sis.admin.auth.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.sis.admin.auth.domain.Role;
import com.ubs.sis.admin.auth.dto.request.RoleRequestDto;
import com.ubs.sis.admin.auth.dto.response.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleRequestDto, RoleResponseDto> {

    @Mappings({
            @Mapping(ignore = true, target = "grants.component.children"),
            @Mapping(ignore = true, target = "grants.component.actions")
    })
    RoleResponseDto mapEntityToResponseDto(Role entity);
}