package com.ubs.sis.admin.auth.service.impl;

import com.ubs.sis.admin.auth.domain.Action;
import com.ubs.sis.admin.auth.dto.mapper.ActionMapper;
import com.ubs.sis.admin.auth.dto.request.ActionRequestDto;
import com.ubs.sis.admin.auth.repository.ActionRepository;
import com.ubs.sis.admin.auth.service.ActionService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;

    public ActionServiceImpl(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    @Override
    public ActionRepository getRepository() {
        return actionRepository;
    }

    @Override
    public ActionMapper getModelMapper() {
        return actionMapper;
    }

    @Override
    public Action validateAndMapToEntityForSave(ActionRequestDto requestDto) {
        return actionMapper.mapRequestDtoToEntity(requestDto);
    }

    @Override
    public Action validateAndMapToEntityForUpdate(ActionRequestDto requestDto, Action entity) {
        entity.setCode(requestDto.getCode());
        entity.setName(requestDto.getName());
        return entity;
    }

    @Override
    public Set<Action> findActionEntitiesByIds(Set<Long> actionIds) {
        return new HashSet<>(actionRepository.findAllById(actionIds));
    }
}
