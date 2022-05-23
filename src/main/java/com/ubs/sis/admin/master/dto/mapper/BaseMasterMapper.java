package com.ubs.sis.admin.master.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.commons.dto.request.BaseRequestDto;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.master.domain.MasterEntity;
import com.ubs.sis.admin.master.dto.response.MasterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface BaseMasterMapper<Entity extends MasterEntity, RequestDto extends BaseRequestDto, ResponseDto extends MasterResponseDto>
        extends BaseMapper<Entity, RequestDto, ResponseDto> {

    @Mapping(source = "structureMaster.id",target = "structureMasterId")
    @Override
    ResponseDto mapEntityToResponseDto(Entity entity);
}
