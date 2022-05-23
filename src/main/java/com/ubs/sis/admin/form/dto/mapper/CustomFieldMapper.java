package com.ubs.sis.admin.form.dto.mapper;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.sis.admin.form.domain.custom_form.FormField;
import com.ubs.sis.admin.form.dto.request.FieldRequestDto;
import com.ubs.sis.admin.form.dto.response.FieldResponseDto;
import org.mapstruct.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper(componentModel = "spring")
@Transactional
public interface CustomFieldMapper extends BaseMapper<FormField, FieldRequestDto, FieldResponseDto> {
}
