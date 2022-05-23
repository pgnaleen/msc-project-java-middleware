package com.ubs.sis.admin.master.service.impl;

import com.ubs.commons.dto.response.Response;
import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.master.domain.StructureMaster;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.master.dto.filter.StructureMasterFilterDto;
import com.ubs.sis.admin.master.dto.mapper.StructureMasterMapper;
import com.ubs.sis.admin.master.dto.request.StructureMasterRequestDto;
import com.ubs.sis.admin.master.dto.response.StructureMasterResponseDto;
import com.ubs.sis.admin.master.repository.StructureMasterRepository;
import com.ubs.sis.admin.master.repository.specification.StructureMasterSpecification;
import com.ubs.sis.admin.master.service.EntityDataBaseService;
import com.ubs.sis.admin.master.service.StructureMasterService;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StructureMasterServiceImpl implements StructureMasterService, EntityDataBaseService {

    private final StructureMasterRepository structureMasterRepository;
    private final StructureMasterMapper modelMapper;
    private final ApplicationContext context;

    public StructureMasterServiceImpl(StructureMasterRepository structureMasterRepository, StructureMasterMapper modelMapper,
                                      ApplicationContext context) {
        this.structureMasterRepository = structureMasterRepository;
        this.modelMapper = modelMapper;
        this.context = context;
    }

    @Override
    public BaseRepository<StructureMaster> getRepository() {
        return this.structureMasterRepository;
    }

    @Override
    public Specification<StructureMaster> createSpecification(StructureMasterFilterDto filterDto) {
        return new StructureMasterSpecification(filterDto);
    }

    @Override
    public StructureMasterMapper getModelMapper() {
        return this.modelMapper;
    }

    @Override
    public StructureMaster validateAndMapToEntityForSave(StructureMasterRequestDto requestDto) {
        return modelMapper.mapRequestDtoToEntity(requestDto);
    }

    @Override
    public StructureMaster validateAndMapToEntityForUpdate(StructureMasterRequestDto requestDto, StructureMaster originalEntity) {
        originalEntity.setName(requestDto.getName());
        return originalEntity;
    }

    @Override
    public Response<List<StructureMasterResponseDto>> getList(StructureMasterFilterDto filterDto) {
        Response<List<StructureMasterResponseDto>> list = StructureMasterService.super.getList(filterDto);
        List<StructureMasterResponseDto> responses = list.getPayload();
        if (Boolean.TRUE.equals(filterDto.getWithData())) {
            setDataToMasters(responses, filterDto);
        }
        return list;
    }

    @Override
    public Response<StructureMasterResponseDto> getDtoById(Long id, BusinessEntity businessEntity) {
        final Response<StructureMasterResponseDto> dtoResponse = getDtoById(id);

        setDataToMaster(dtoResponse.getPayload(),  null, null, dtoResponse.getPayload().getReferenceId(), businessEntity, null);

        return dtoResponse;
    }

    private void setDataToMasters(List<StructureMasterResponseDto> responses, StructureMasterFilterDto filterDto) {
        List<Long> ids = responses.stream().map(StructureMasterResponseDto::getReferenceId)
                .filter(Objects::nonNull).collect(Collectors.toList());

        setDataToMasters(responses, null, null, ids, filterDto.getBusinessEntity());

    }


    @Override
    public ApplicationContext getApplicationContext() {
        return context;
    }
}
