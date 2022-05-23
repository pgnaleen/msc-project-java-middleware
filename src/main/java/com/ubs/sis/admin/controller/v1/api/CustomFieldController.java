package com.ubs.sis.admin.controller.v1.api;

import com.ubs.commons.dto.response.Response;
import com.ubs.sis.admin.form.domain.enums.Status;
import com.ubs.sis.admin.form.dto.filter.FieldFilterDto;
import com.ubs.sis.admin.form.dto.request.FieldRequestDto;
import com.ubs.sis.admin.form.dto.response.FieldResponseDto;
import com.ubs.sis.admin.form.service.CustomFieldService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/custom-fields")
public class CustomFieldController {

    private final CustomFieldService customFieldService;

    public CustomFieldController(CustomFieldService customFieldService) {
        this.customFieldService = customFieldService;
    }

    @PostMapping
    public Response<FieldResponseDto> createCustomField(@RequestBody FieldRequestDto fieldRequestDto) {
        return customFieldService.create(fieldRequestDto);
    }

    @GetMapping
    public Response<List<FieldResponseDto>> getCustomFields(@RequestParam(required = false) Status status) {
        FieldFilterDto fieldFilterDto = new FieldFilterDto();
        fieldFilterDto.setIsCustom(true);
        return customFieldService.getList(fieldFilterDto);
    }
}
