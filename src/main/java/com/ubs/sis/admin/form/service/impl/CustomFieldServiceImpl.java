package com.ubs.sis.admin.form.service.impl;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.form.domain.custom_form.FormField;
import com.ubs.sis.admin.form.dto.filter.FieldFilterDto;
import com.ubs.sis.admin.form.dto.mapper.CustomFieldMapper;
import com.ubs.sis.admin.form.dto.request.FieldRequestDto;
import com.ubs.sis.admin.form.dto.response.FieldResponseDto;
import com.ubs.sis.admin.form.repository.FormFieldRepository;
import com.ubs.sis.admin.form.repository.specification.FieldSpecification;
import com.ubs.sis.admin.form.service.CustomFieldService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomFieldServiceImpl implements CustomFieldService {

    private final FormFieldRepository formFieldRepository;
    private final CustomFieldMapper customFieldMapper;

    public CustomFieldServiceImpl(FormFieldRepository formFieldRepository, CustomFieldMapper customFieldMapper) {
        this.formFieldRepository = formFieldRepository;
        this.customFieldMapper = customFieldMapper;
    }

    @Override
    public BaseRepository<FormField> getRepository() {
        return formFieldRepository;
    }

    @Override
    public BaseMapper<FormField, FieldRequestDto, FieldResponseDto> getModelMapper() {
        return customFieldMapper;
    }

    @Override
    public Specification<FormField> createSpecification(FieldFilterDto filterDto) {
        return new FieldSpecification(filterDto);
    }

    @Override
    public FormField validateAndMapToEntityForSave(FieldRequestDto requestDto) {
        FormField formField = customFieldMapper.mapRequestDtoToEntity(requestDto);
        formField.setIsCustom(true);
        formField.setIsInternal(true);
        return formField;
    }

    @Override
    public FormField validateAndMapToEntityForUpdate(FieldRequestDto requestDto, FormField entity) {
        return entity;
    }
}
