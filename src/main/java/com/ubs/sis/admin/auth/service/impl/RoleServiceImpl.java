package com.ubs.sis.admin.auth.service.impl;

import com.ubs.sis.admin.auth.domain.Grant;
import com.ubs.sis.admin.auth.domain.Role;
import com.ubs.sis.admin.auth.dto.mapper.RoleMapper;
import com.ubs.sis.admin.auth.dto.request.GrantRequestDto;
import com.ubs.sis.admin.auth.dto.request.RoleRequestDto;
import com.ubs.sis.admin.auth.repository.GrantRepository;
import com.ubs.sis.admin.auth.repository.RoleRepository;
import com.ubs.sis.admin.auth.service.ActionService;
import com.ubs.sis.admin.auth.service.ComponentService;
import com.ubs.sis.admin.auth.service.GrantService;
import com.ubs.sis.admin.auth.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final ComponentService componentService;
    private final ActionService actionService;
    private final GrantService grantService;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, ComponentService componentService,
                           ActionService actionService, GrantService grantService) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.componentService = componentService;
        this.actionService = actionService;
        this.grantService = grantService;
    }

    @Override
    public RoleRepository getRepository() {
        return roleRepository;
    }

    @Override
    public RoleMapper getModelMapper() {
        return roleMapper;
    }

    @Override
    public Role validateAndMapToEntityForSave(RoleRequestDto requestDto) {
        Role role = roleMapper.mapRequestDtoToEntity(requestDto);
        role.setGrants(createGrants(requestDto.getGrants()));
        return role;
    }

    @Override
    public Role validateAndMapToEntityForUpdate(RoleRequestDto requestDto, Role entity) {
        entity.setName(requestDto.getName());
        entity.setCode(requestDto.getCode());
        grantService.deleteAll(entity.getGrants());
        entity.setGrants(createGrants(requestDto.getGrants()));
        return entity;
    }

    private Set<Grant> createGrants(Set<GrantRequestDto> grantRequestDtoList) {
        return grantRequestDtoList.stream().map(grantRequestDto -> {
            Grant grant = new Grant();
            grant.setComponent(componentService.getEntityById(grantRequestDto.getComponentId()));
            grant.setAction(actionService.getEntityById(grantRequestDto.getActionId()));
            grant.setTenantId(grantRequestDto.getTenantId());
            return grant;
        }).collect(Collectors.toSet());
    }

    @Override
    public Role getRoleById(long roleId) {
        return roleRepository.getById(roleId);
    }
}
