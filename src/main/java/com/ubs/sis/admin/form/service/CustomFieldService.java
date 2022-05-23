package com.ubs.sis.admin.form.service;

import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.form.domain.custom_form.FormField;
import com.ubs.sis.admin.form.dto.filter.FieldFilterDto;
import com.ubs.sis.admin.form.dto.request.FieldRequestDto;
import com.ubs.sis.admin.form.dto.response.FieldResponseDto;

public interface CustomFieldService extends BaseService<FormField, FieldRequestDto, FieldResponseDto, FieldFilterDto> {
}
