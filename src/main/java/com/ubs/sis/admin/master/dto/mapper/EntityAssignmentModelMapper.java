package com.ubs.sis.admin.master.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.sis.admin.master.domain.EntityAssignment;
import com.ubs.sis.admin.master.dto.request.EntityAssignmentRequest;
import com.ubs.sis.admin.master.dto.response.EntityAssignmentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")

public interface EntityAssignmentModelMapper extends BaseMapper<EntityAssignment, EntityAssignmentRequest, EntityAssignmentResponseDto> {

    @Mapping(source = "entity.businessEntity",target = "businessEntity")
    @Mapping(source = "parent.entity.businessEntity",target = "parentBusinessEntity")
    @Mapping(source = "parent.entity.referenceId",target = "parentReferenceId")
    @Mapping(source = "parent.id",target = "parentAssignmentId")
    @Override
    EntityAssignmentResponseDto mapEntityToResponseDto(EntityAssignment entity);
}
