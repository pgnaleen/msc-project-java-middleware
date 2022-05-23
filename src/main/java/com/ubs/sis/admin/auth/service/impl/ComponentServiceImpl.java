package com.ubs.sis.admin.auth.service.impl;

import com.ubs.commons.exception.GearsException;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.sis.admin.auth.domain.Component;
import com.ubs.sis.admin.auth.dto.mapper.ComponentMapper;
import com.ubs.sis.admin.auth.dto.request.ComponentInitRequestDto;
import com.ubs.sis.admin.auth.dto.request.ComponentRequestDto;
import com.ubs.sis.admin.auth.dto.response.ComponentResponseDto;
import com.ubs.sis.admin.auth.repository.ComponentRepository;
import com.ubs.sis.admin.auth.service.ActionService;
import com.ubs.sis.admin.auth.service.ComponentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentMapper componentMapper;
    private final ActionService actionService;

    public ComponentServiceImpl(ComponentRepository componentRepository, ComponentMapper componentMapper, ActionService actionService) {
        this.componentRepository = componentRepository;
        this.componentMapper = componentMapper;
        this.actionService = actionService;
    }

    @Override
    public ComponentRepository getRepository() {
        return componentRepository;
    }

    @Override
    public ComponentMapper getModelMapper() {
        return componentMapper;
    }

    @Override
    public Component validateAndMapToEntityForSave(ComponentRequestDto requestDto) {
        Component component = componentMapper.mapRequestDtoToEntity(requestDto);

        if (requestDto.getParentId() != null) {
            component.setParent(getEntityById(requestDto.getParentId()));
        }

        return component;
    }

    @Override
    public Component validateAndMapToEntityForUpdate(ComponentRequestDto requestDto, Component entity) {
        entity.setCode(requestDto.getCode());
        entity.setName(requestDto.getName());
        return entity;
    }

    @Override
    public List<ComponentResponseDto> getRolesMatrices() {
        return mapEntityListToResponseDtoList(componentRepository.findByParent(null));
    }

    @Override
    public ComponentResponseDto initRolesMatrix(ComponentInitRequestDto requestDto) {
        validateMatrix(requestDto);
        Component component = mapComponent(null, requestDto);

        componentRepository.save(component);
        return mapToResponseDto(component);
    }

    private List<ComponentResponseDto> mapEntityListToResponseDtoList(List<Component> components) {
        if (components == null) {
            return new ArrayList<>();
        }

        return components.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    private ComponentResponseDto mapToResponseDto(Component entity) {
        ComponentResponseDto componentResponseDto = componentMapper.mapEntityToResponseDto(entity);
        componentResponseDto.setParentId(entity.getParent() != null ? entity.getParent().getId() : null);

        if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
            componentResponseDto.setChildren(mapEntityListToResponseDtoList(entity.getChildren()));
        }

        return componentResponseDto;
    }

    private void validateMatrix(ComponentInitRequestDto requestDto) {
        if (componentRepository.existsByCode(requestDto.getCode())) {
            throw new GearsException(GearsResponseStatus.DUPLICATED_ENTRY, "Code " + requestDto.getCode() + " is already exists.");
        }

        if (requestDto.getChildren() != null && !requestDto.getChildren().isEmpty()) {
            requestDto.getChildren().forEach(this::validateMatrix);
        }
    }

    private Component mapComponent(Component parent, ComponentInitRequestDto requestDto) {
        Component component = new Component();
        component.setParent(parent);
        component.setName(requestDto.getName());
        component.setCode(requestDto.getCode());
        component.setTenantId(requestDto.getTenantId());
        if (requestDto.getChildren() != null && !requestDto.getChildren().isEmpty()) {
            component.setChildren(requestDto.getChildren().stream()
                    .map(componentInitRequestDto -> mapComponent(component, componentInitRequestDto))
                    .collect(Collectors.toList()));
        }

        if (requestDto.getActionIds() != null && !requestDto.getActionIds().isEmpty()) {
            component.setActions(actionService.findActionEntitiesByIds(requestDto.getActionIds()));
        }

        return component;
    }
}
