package com.ubs.sis.admin.master.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.sis.admin.master.domain.StructureMaster;
import com.ubs.sis.admin.master.dto.request.StructureMasterRequestDto;
import com.ubs.sis.admin.master.dto.response.StructureMasterResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructureMasterMapper extends BaseMapper<StructureMaster, StructureMasterRequestDto, StructureMasterResponseDto> {
}
