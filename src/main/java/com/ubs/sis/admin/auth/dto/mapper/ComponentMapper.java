package com.ubs.sis.admin.auth.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.sis.admin.auth.domain.Component;
import com.ubs.sis.admin.auth.dto.request.ComponentRequestDto;
import com.ubs.sis.admin.auth.dto.response.ComponentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComponentMapper extends BaseMapper<Component, ComponentRequestDto, ComponentResponseDto> {
}