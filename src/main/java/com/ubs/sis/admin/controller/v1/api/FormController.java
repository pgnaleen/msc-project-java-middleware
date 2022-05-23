package com.ubs.sis.admin.controller.v1.api;

import com.ubs.commons.dto.response.Response;
import com.ubs.sis.admin.form.domain.enums.Status;
import com.ubs.sis.admin.form.dto.request.FieldRequestDto;
import com.ubs.sis.admin.form.dto.request.FieldUpdateRequest;
import com.ubs.sis.admin.form.dto.response.FieldUpdateResponse;
import com.ubs.sis.admin.form.dto.response.FormDto;
import com.ubs.sis.admin.form.dto.response.FormStructureResponseDto;
import com.ubs.sis.admin.form.service.CustomFormsService;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forms")
public class FormController {

    private final CustomFormsService customFormsService;


    public FormController(CustomFormsService customFormsService) {
        this.customFormsService = customFormsService;
    }

    @GetMapping("/{formId}")
    public Response<FormStructureResponseDto> getFormStructureById(@PathVariable Long formId) {
        return customFormsService.getFormStructureById(formId);
    }

    @GetMapping("/by-descriptor/{formDescriptor}")
    public Response<FormStructureResponseDto> getFormStructureByDescriptor(@PathVariable CustomFormSupportedEntity formDescriptor) {
        return customFormsService.getFormStructureByDescriptor(formDescriptor);
    }

    @GetMapping
    public Response<List<FormDto>> getForms(@RequestParam(required = false) Status status) {
        return customFormsService.getForms(status);
    }

    @PutMapping("/{formId}")
    public Response<FieldUpdateResponse> updateForm(@PathVariable Long formId, @RequestBody FieldUpdateRequest fieldUpdateRequest) {
        return customFormsService.updateFields(formId, fieldUpdateRequest);
    }

    @GetMapping("/{formId}/available-fields")
    public Response<List<FieldRequestDto>> getAvailableFields(@PathVariable Long formId) {
        return customFormsService.getAvailableFields(formId);
    }
}
