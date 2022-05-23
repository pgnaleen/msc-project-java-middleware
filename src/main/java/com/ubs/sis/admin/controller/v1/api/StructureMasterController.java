package com.ubs.sis.admin.controller.v1.api;

import com.ubs.commons.dto.response.Response;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.master.dto.filter.StructureMasterFilterDto;
import com.ubs.sis.admin.master.dto.response.StructureMasterResponseDto;
import com.ubs.sis.admin.master.service.StructureMasterService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/masters")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class StructureMasterController {

    private final StructureMasterService structureMasterService;

    public StructureMasterController(StructureMasterService structureMasterService) {
        this.structureMasterService = structureMasterService;
    }

    @GetMapping("/{businessEntity}/{structureMasterId}")
    public Response<StructureMasterResponseDto> getById(
            @PathVariable("businessEntity") BusinessEntity businessEntity,
            @PathVariable("structureMasterId") Long structureMasterId) {
        return structureMasterService.getDtoById(structureMasterId, businessEntity);
    }

    @GetMapping("/{businessEntity}")
    public Response<List<StructureMasterResponseDto>> getList(
            @PathVariable("businessEntity") BusinessEntity businessEntity,
            @Valid StructureMasterFilterDto filterDto) {
        filterDto.setBusinessEntity(businessEntity);
        return structureMasterService.getList(filterDto);
    }
}
