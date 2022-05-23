package com.ubs.sis.admin.form.service.impl;

import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.commons.dto.request.BaseRequestDto;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.commons.dto.response.Response;
import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.form.domain.custom_form.*;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import com.ubs.sis.admin.form.domain.enums.Status;
import com.ubs.sis.admin.form.dto.FormDefinitionDescriptor;
import com.ubs.sis.admin.form.dto.request.FieldRequestDto;
import com.ubs.sis.admin.form.dto.request.FieldUpdateRequest;
import com.ubs.sis.admin.form.dto.request.ValidationDto;
import com.ubs.sis.admin.form.dto.response.FieldUpdateResponse;
import com.ubs.sis.admin.form.dto.response.FormDto;
import com.ubs.sis.admin.form.dto.response.FormStructureResponseDto;
import com.ubs.sis.admin.form.repository.*;
import com.ubs.sis.admin.form.repository.specification.FormDefinitionSpecification;
import com.ubs.sis.admin.form.repository.specification.FormHasFieldSpecification;
import com.ubs.sis.admin.form.repository.specification.FormSpecification;
import com.ubs.sis.admin.form.service.CustomFormsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomFormServiceImpl extends CustomFormsAbstractService implements CustomFormsService {

    private final FormDefinitionRepository formDefinitionRepository;

    public CustomFormServiceImpl(FormRepository formRepository,
                                 FormFieldRepository formFieldRepository,
                                 FormHasFieldRepository formHasFieldRepository,
                                 RecordValueRepository recordValueRepository,
                                 FormRecordRepository formRecordRepository,
                                 FormDefinitionRepository formDefinitionRepository) {
        super(formRepository,
                formFieldRepository, formHasFieldRepository,
                recordValueRepository,
                formRecordRepository);
        this.formDefinitionRepository = formDefinitionRepository;
    }

    @Override
    public BaseRepository<Form> getRepository() {
        return null;
    }

    @Override
    public BaseMapper<Form, BaseRequestDto, BaseResponseDto> getModelMapper() {
        return null;
    }

    @Override
    public Form validateAndMapToEntityForSave(BaseRequestDto requestDto) {
        return null;
    }

    @Override
    public Form validateAndMapToEntityForUpdate(BaseRequestDto requestDto, Form entity) {
        return null;
    }


    @Override
    public Response<FormStructureResponseDto> getFormStructureById(Long id) {
        Form form = formRepository.getById(id);
        return getResponse(getFormStructureResponse(form.getDescriptor()));
    }

    @Override
    public Response<List<FormDto>> getForms(Status status) {
        List<Form> all = formRepository.findAll(Specification.where(FormSpecification.withStatus(status)));

        return getResponse( all.stream().map(form -> new FormDto(
                form.getId(), form.getName(), form.getDescriptor(), form.getGroup().getGroupName(), form.getStatus()
        )).collect(Collectors.toList()));
    }

    @Override
    public Response<FieldUpdateResponse> removeFields(Long formId, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return getResponse(new FieldUpdateResponse(), "Nothing to delete");
        }

        List<Long> deletableIds = new ArrayList<>();
        List<String> errorFieldLabels = new ArrayList<>();
        for (Long formHasFieldId : ids) {
            List<RecordValue> recordValueOptional = recordValueRepository.findByFormHasFieldFormIdAndFormHasFieldId(formId, formHasFieldId);
            if (!recordValueOptional.isEmpty()) {
                errorFieldLabels.add(recordValueOptional.get(0).getFormHasField().getLabel());
            } else {
                deletableIds.add(formHasFieldId);
            }
        }

        List<FormHasField> formHasFieldList = formHasFieldRepository.findAllById(deletableIds);
        formHasFieldList.forEach(formHasField -> formHasField.setDeleted(true));
        formHasFieldRepository.saveAll(formHasFieldList);

        if (errorFieldLabels.isEmpty()) {
            return getResponse(new FieldUpdateResponse(deletableIds, null), "Successfully deleted");
        } else {
            return getResponse(new FieldUpdateResponse(deletableIds, errorFieldLabels), "Failed to delete some fields");
        }
    }

    @Override
    public Response<List<FieldRequestDto>> getAvailableFields(Long formId) {
        return null;
    }

    @Override
    public Response<FieldUpdateResponse> updateFields(Long formId, FieldUpdateRequest fieldUpdateRequest) {
        Response<FieldUpdateResponse> fieldUpdateResponse = removeFields(formId, fieldUpdateRequest.getRemoveIds());

        Form form = formRepository.getById(formId);

        List<FieldRequestDto> fields = fieldUpdateRequest.getFields();

        if (fields == null || fields.isEmpty()) {
            if (fieldUpdateRequest.getRemoveIds() == null || fieldUpdateRequest.getRemoveIds().isEmpty()) {
                fieldUpdateResponse.setMessage("Nothing to update");
            }

            return fieldUpdateResponse;
        }

        fields.removeIf(fieldRequestDto -> fieldUpdateRequest.getRemoveIds().contains(fieldRequestDto.getId()));

        List<FormHasField> formHasFields = formHasFieldRepository.findAll(Specification
                .where(FormHasFieldSpecification.withFormId(formId)));

        formHasFields.forEach(formHasField -> {
            Optional<FieldRequestDto> dtoOptional = fields.stream()
                    .filter(fieldRequestDto -> formHasField.getId().equals(fieldRequestDto.getId())).findFirst();

            if(dtoOptional.isPresent()){
                FieldRequestDto fieldRequestDto = dtoOptional.get();

                if (fieldRequestDto.getSortOrder() != null) {
                    formHasField.setSortOrder(fieldRequestDto.getSortOrder());
                }

                if (fieldRequestDto.getIsActive() != null) {
                    formHasField.setIsActive(fieldRequestDto.getIsActive());
                }

                setValidations(formHasField, fieldRequestDto);
            }
        });

        List<FieldRequestDto> newFieldReqs = fields.stream().filter(fieldRequestDto -> fieldRequestDto.getId() == null || fieldRequestDto.getId().equals(0L))
                .collect(Collectors.toList());

        List<FormHasField> newFormFields = newFieldReqs.stream().map(fieldRequestDto -> {
            FormHasField formHasField = new FormHasField();
            formHasField.setIsActive(fieldRequestDto.getIsActive());
            formHasField.setLabel(fieldRequestDto.getKey());
            formHasField.setSortOrder(fieldRequestDto.getSortOrder());
            formHasField.setForm(form);
            formHasField.setIsCustom(true);
            formHasField.setFormField(formFieldRepository.findByKey(fieldRequestDto.getKey()));
            formHasField.setTenantId(1L);

            FieldValidation fieldValidation = new FieldValidation();
            if (fieldRequestDto.getValidations() != null) {
                fieldValidation.setIsRequired(fieldRequestDto.getValidations().getRequired());
                fieldValidation.setMinLength(fieldRequestDto.getValidations().getMinLength());
                fieldValidation.setMaxLength(fieldRequestDto.getValidations().getMaxLength());
                fieldValidation.setTenantId(1L);
            }

            formHasField.setFieldValidation(fieldValidation);
            return formHasField;
        }).collect(Collectors.toList());

        formHasFields.addAll(newFormFields);
        formHasFieldRepository.saveAll(formHasFields);

        fieldUpdateResponse.setMessage("Successfully updated");
        return fieldUpdateResponse;
    }

    private void setValidations(FormHasField formHasField, FieldRequestDto fieldRequestDto) {
        if (fieldRequestDto.getValidations() == null) {
            return;
        }

        FieldValidation fieldValidation = formHasField.getFieldValidation();

        ValidationDto validations = fieldRequestDto.getValidations();
        if (fieldValidation != null) {
            if (validations.getRequired() != null) {
                fieldValidation.setIsRequired(validations.getRequired());
            }

            if (validations.getMinLength() != null) {
                fieldValidation.setMinLength(validations.getMinLength());
            }

            if (validations.getMaxLength() != null) {
                fieldValidation.setMaxLength(validations.getMaxLength());
            }
        } else {
            fieldValidation = new FieldValidation();
            fieldValidation.setIsRequired(validations.getRequired());
            fieldValidation.setMinLength(validations.getMinLength());
            fieldValidation.setMaxLength(validations.getMaxLength());
            fieldValidation.setTenantId(1L);
            formHasField.setFieldValidation(fieldValidation);
        }
    }

    @Override
    public Response<FormStructureResponseDto> getFormStructureByDescriptor(CustomFormSupportedEntity formDescriptor) {
        return getResponse(getFormStructureResponse(formDescriptor));
    }

    @PostConstruct
    private void initCustomForms() {
        //Check and Create custom forms for supported entities when startup
        for (CustomFormSupportedEntity value : CustomFormSupportedEntity.values()) {
            if (formRepository.findOne(
                    Specification.where(
                            FormSpecification.withFormDef(value.getFormDefinitionDescriptor())
                    ).and(FormSpecification.withStatus(Status.ACTIVE))
            ).isEmpty()) {
                FormDefinition formDefinition = createFormDefinition(value.getFormDefinitionDescriptor());
                Form form = createFormForDefinition(formDefinition, value);
            }
        }
    }

    private Form createFormForDefinition(FormDefinition formDefinition,
                                         CustomFormSupportedEntity customFormSupportedEntity) {
        if (formRepository.findOne(
                Specification.where(
                        FormSpecification.withFormDef(formDefinition.getId())
                ).and(FormSpecification.withStatus(Status.ACTIVE))
        ).isEmpty()) {
            log.info("Creating a form  for FD_ID : {} ", formDefinition.getId());

            Form form = new Form();
            form.setName(customFormSupportedEntity.name());
            form.setStatus(Status.ACTIVE);
            form.setFormDefinitionId(formDefinition.getId());
            form.setDescriptor(customFormSupportedEntity);
            form.setGroup(customFormSupportedEntity.getGroup());
            formRepository.save(form);
            return form;
        }
        return null;
    }

    private FormDefinition createFormDefinition(FormDefinitionDescriptor formDefinitionDescriptor) {
        Optional<FormDefinition> one = formDefinitionRepository.findOne(
                Specification.where(
                        FormDefinitionSpecification.withBusinessEntities(
                                formDefinitionDescriptor.getBusinessEntity(),
                                formDefinitionDescriptor.getAssignedBusinessEntity())
                )
        );
        if (one.isPresent()) {
            return one.get();
        }
        log.info("Creating a form definition for : {} ", formDefinitionDescriptor);
        FormDefinition formDefinition = new FormDefinition();
        formDefinition.setBusinessEntity(formDefinitionDescriptor.getBusinessEntity());
        formDefinition.setAssignedBusinessEntity(formDefinitionDescriptor.getAssignedBusinessEntity());
        return formDefinitionRepository.saveAndFlush(formDefinition);

    }
}
