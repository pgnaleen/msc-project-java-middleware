package com.ubs.sis.admin.master.service;

import com.ubs.commons.dto.response.Response;
import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.master.domain.StructureMaster;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.master.dto.filter.StructureMasterFilterDto;
import com.ubs.sis.admin.master.dto.request.StructureMasterRequestDto;
import com.ubs.sis.admin.master.dto.response.StructureMasterResponseDto;

public interface StructureMasterService extends BaseService<StructureMaster, StructureMasterRequestDto, StructureMasterResponseDto,
        StructureMasterFilterDto> {

    Response<StructureMasterResponseDto> getDtoById(Long id, BusinessEntity businessEntity);
}
