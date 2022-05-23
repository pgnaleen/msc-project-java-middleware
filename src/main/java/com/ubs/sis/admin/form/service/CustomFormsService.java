package com.ubs.sis.admin.form.service;

import com.ubs.commons.dto.filter.BaseFilterDto;
import com.ubs.commons.dto.request.BaseRequestDto;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.commons.dto.response.Response;
import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.form.domain.custom_form.Form;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import com.ubs.sis.admin.form.domain.enums.Status;
import com.ubs.sis.admin.form.dto.request.FieldRequestDto;
import com.ubs.sis.admin.form.dto.request.FieldUpdateRequest;
import com.ubs.sis.admin.form.dto.response.FieldUpdateResponse;
import com.ubs.sis.admin.form.dto.response.FormDto;
import com.ubs.sis.admin.form.dto.response.FormStructureResponseDto;

import java.util.List;

public interface CustomFormsService extends BaseService<Form, BaseRequestDto, BaseResponseDto,
        BaseFilterDto> {

    Response<FormStructureResponseDto> getFormStructureById(Long id);

    Response<List<FormDto>> getForms(Status status);

    Response<FieldUpdateResponse> removeFields(Long formId, List<Long> ids);

    Response<List<FieldRequestDto>> getAvailableFields(Long formId);

    Response<FieldUpdateResponse> updateFields(Long formId, FieldUpdateRequest fieldUpdateRequest);

    Response<FormStructureResponseDto> getFormStructureByDescriptor(CustomFormSupportedEntity formDescriptor);
}
