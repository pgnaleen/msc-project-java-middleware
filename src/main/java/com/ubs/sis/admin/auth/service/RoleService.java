package com.ubs.sis.admin.auth.service;

import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.auth.domain.Role;
import com.ubs.sis.admin.auth.dto.filter.RoleFilterDto;
import com.ubs.sis.admin.auth.dto.request.RoleRequestDto;
import com.ubs.sis.admin.auth.dto.response.RoleResponseDto;

public interface RoleService extends BaseService<Role, RoleRequestDto, RoleResponseDto, RoleFilterDto> {
    public Role getRoleById(long roleId);
}