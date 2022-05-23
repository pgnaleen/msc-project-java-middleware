package com.ubs.sis.admin.auth.service;

import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.auth.dto.request.ActionRequestDto;
import com.ubs.sis.admin.auth.dto.response.ActionResponseDto;
import com.ubs.sis.admin.auth.dto.filter.ActionFilterDto;
import com.ubs.sis.admin.auth.domain.Action;

import java.util.Set;

public interface ActionService extends BaseService<Action, ActionRequestDto, ActionResponseDto, ActionFilterDto> {

    Set<Action> findActionEntitiesByIds(Set<Long> actionIds);
}