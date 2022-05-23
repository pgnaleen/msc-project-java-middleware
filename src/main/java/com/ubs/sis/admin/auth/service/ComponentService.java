package com.ubs.sis.admin.auth.service;

import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.auth.domain.Component;
import com.ubs.sis.admin.auth.dto.filter.ComponentFilterDto;
import com.ubs.sis.admin.auth.dto.request.ComponentInitRequestDto;
import com.ubs.sis.admin.auth.dto.request.ComponentRequestDto;
import com.ubs.sis.admin.auth.dto.response.ComponentResponseDto;

import java.util.List;

public interface ComponentService extends BaseService<Component, ComponentRequestDto, ComponentResponseDto, ComponentFilterDto> {
    List<ComponentResponseDto> getRolesMatrices();

    ComponentResponseDto initRolesMatrix(ComponentInitRequestDto componentInitRequestDto);
}