package com.ubs.sis.admin.auth.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.sis.admin.auth.domain.Action;
import com.ubs.sis.admin.auth.dto.request.ActionRequestDto;
import com.ubs.sis.admin.auth.dto.response.ActionResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionMapper extends BaseMapper<Action, ActionRequestDto, ActionResponseDto> {
}