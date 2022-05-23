package com.ubs.sis.admin.form.service.impl;

import com.ubs.commons.dto.response.Response;
import com.ubs.commons.exception.GearsException;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.sis.admin.form.domain.SupportCustomForm;
import com.ubs.sis.admin.form.domain.custom_form.*;
import com.ubs.sis.admin.form.dto.response.FormDisplayParametersResponseDto;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import com.ubs.sis.admin.form.domain.enums.FormDataType;
import com.ubs.sis.admin.form.domain.enums.Status;
import com.ubs.sis.admin.form.dto.request.FieldValueDto;
import com.ubs.sis.admin.form.dto.request.SimpleFieldValueDto;
import com.ubs.sis.admin.form.dto.request.ValidationDto;
import com.ubs.sis.admin.form.dto.response.FormHeaderDto;
import com.ubs.sis.admin.form.dto.response.FormResponseDto;
import com.ubs.sis.admin.form.dto.response.FormStructureResponseDto;
import com.ubs.sis.admin.form.exception.FormsException;
import com.ubs.sis.admin.form.repository.*;
import com.ubs.sis.admin.form.repository.specification.FormHasFieldSpecification;
import com.ubs.sis.admin.form.repository.specification.FormSpecification;
import com.ubs.sis.admin.util.MiscUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public abstract class CustomFormsAbstractService {
    protected final FormRepository formRepository;
    protected final FormFieldRepository formFieldRepository;
    protected final FormHasFieldRepository formHasFieldRepository;
    protected final RecordValueRepository recordValueRepository;
    protected final FormRecordRepository formRecordRepository;
    protected static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected CustomFormsAbstractService(FormRepository formRepository,
                                         FormFieldRepository formFieldRepository, FormHasFieldRepository formHasFieldRepository,
                                         RecordValueRepository recordValueRepository, FormRecordRepository formRecordRepository) {
        this.formRepository = formRepository;
        this.formFieldRepository = formFieldRepository;
        this.formHasFieldRepository = formHasFieldRepository;
        this.recordValueRepository = recordValueRepository;
        this.formRecordRepository = formRecordRepository;
    }

    protected List<FormHeaderDto> getHeadersForForm(Long id) {
        List<FormHasField> all = formHasFieldRepository.findAll(Specification.where(FormHasFieldSpecification.withFormId(id)));

        return all.stream().map(formHasField -> {
            FormField formField = formHasField.getFormField();
            return new FormHeaderDto(formHasField.getId(), formField.getId(), formField.getKey(),
                    formHasField.getLabel(),
                    formField.getDataType(),
                    formHasField.getSortOrder(),
                    formHasField.getIsCustom(),
                    formHasField.getIsActive(),
                    getValidation(formHasField),
                    getDisplayParameters(formHasField)
            );
        }).sorted(Comparator.comparing(FormHeaderDto::getSortOrder)).collect(Collectors.toList());
    }

    protected List<FieldValueDto> getDataForForm(FormRecord formRecord) {
        if (formRecord != null) {

            return formRecord.getValues().stream()
                    .filter(recordValue -> recordValue.getFormHasField().getIsActive())
                    .map(recordValue -> new FieldValueDto(recordValue.getId(),
                            recordValue.getFormHasField().getFormField().getDataType(),
                            recordValue.getFormHasField().getIsCustom(),
                            getValidation(recordValue.getFormHasField()),
                            recordValue.getFormHasField().getFormField().getKey(),
                            recordValue.getValue()
                    )
            ).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    protected List<FieldValueDto> getDataForForm(Long formId) {
        return getDataForForm(formRecordRepository.findById(formId).orElse(null));
    }


    private ValidationDto getValidation(FormHasField formHasField) {
        return formHasField.getFieldValidation() != null ? new ValidationDto(formHasField.getFieldValidation()) : new ValidationDto(formHasField.getFormField().getValidations());
    }

    private FormDisplayParametersResponseDto getDisplayParameters(FormHasField formHasField) {
        return formHasField.getDisplayParameters() != null ?
                FormDisplayParametersResponseDto.fromEntity(formHasField.getDisplayParameters()) :
                FormDisplayParametersResponseDto.fromEntity(formHasField.getFormField().getDisplayParameters());
    }

    protected <T> Response<T> getResponse(T obj) {
        return getResponse(obj, "Success");
    }

    protected <T> Response<T> getResponse(T obj, String message) {
        Response<T> tResponse = new Response<>();
        tResponse.setPayload(obj);
        tResponse.setMessage(message);
        tResponse.setStatusCode(GearsResponseStatus.OK.getBusinessStatusCode());
        return tResponse;
    }


    protected Form getForm(CustomFormSupportedEntity customFormSupportedEntity) {
        Optional<Form> one = formRepository.findOne(
                Specification.where(
                        FormSpecification.withFormDef(customFormSupportedEntity.getFormDefinitionDescriptor())
                ).and(FormSpecification.withStatus(Status.ACTIVE))
        );

        return one.orElseThrow(() -> new FormsException("Form not found or multiple definitions found"));
    }

    protected FormStructureResponseDto getFormStructureResponse(CustomFormSupportedEntity customFormSupportedEntity) {
        Form form = getForm(customFormSupportedEntity);
        FormStructureResponseDto formStructureResponseDto = getFormStructureResponse(form.getId());
        formStructureResponseDto.setFormBasicData(form);
        return formStructureResponseDto;
    }

    protected FormStructureResponseDto getFormStructureResponse(Long id) {
        return FormStructureResponseDto.builder()
                .headers(getHeadersForForm(id))
                .build();
    }

    protected boolean removeFormRecord(SupportCustomForm supportCustomForm) {
        FormRecord formRecord = supportCustomForm.getFormRecord();
        formRecord.setDeleted(true);
        List<RecordValue> values = formRecord.getValues();
        for (RecordValue value : values) {
            value.setDeleted(true);
        }
        formRecordRepository.save(formRecord);
        recordValueRepository.saveAll(values);
        return true;
    }


    protected void setFormResponseData(FormResponseDto formResponseDto, SupportCustomForm supportCustomForm) {
        FormRecord formRecord = supportCustomForm.getFormRecord();
        List<SimpleFieldValueDto> customFormFieldValues = getCustomFormFieldValues(formRecord);
        formResponseDto.setCustomFields(customFormFieldValues);
    }

    private List<SimpleFieldValueDto> getCustomFormFieldValues(FormRecord formRecord) {
        List<FieldValueDto> dataForForm = getDataForForm(formRecord);
        return MiscUtils.getEmptyIfNull(dataForForm).stream()
                .map(fieldValueDto -> new SimpleFieldValueDto(
                        fieldValueDto.getId(), fieldValueDto.getKey(), fieldValueDto.getValue())
        ).collect(Collectors.toList());
    }

    private String getValueFromField(String fieldName, Object o) {
        if (fieldName != null && o != null) {
            try {
                Object o1 = FieldUtils.readField(o, fieldName, true);
                Field declaredField = FieldUtils.getDeclaredField(o.getClass(), fieldName, true);
                if (o1 != null) {
                    if (declaredField.getType().equals(Date.class)) {
                        return dateFormat.format(o1);
                    } else {
                        return String.valueOf(o1);
                    }
                }

            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;

    }

    protected void updateFieldValuesForRecord(FormRecord formRecord, List<SimpleFieldValueDto> customFields) {
        List<FormHasField> formHasFields = formHasFieldRepository.findAll(
                Specification.where(FormHasFieldSpecification.withFormId(formRecord.getForm().getId())));

        MiscUtils.getEmptyIfNull(customFields).forEach(recordValue -> {
            Optional<RecordValue> optional = formRecord.getValues().stream()
                    .filter(existingVal -> existingVal.getFormHasField().getFormField().getKey().equals(recordValue.getKey()))
                    .findFirst();

            if (optional.isPresent()) {
                RecordValue existingRecordValue = optional.get();
                if (existingRecordValue.getFormHasField().getFormField().getDataType() == FormDataType.NUMBER_DECIMAL || existingRecordValue.getFormHasField().getFormField().getDataType() == FormDataType.NUMBER_INTEGER) {
                    existingRecordValue.setNumberValue(Double.parseDouble(recordValue.getValue().toString()));
                } else {
                    existingRecordValue.setTextValue(recordValue.getValue().toString());
                }
            } else {
                if (recordValue.getValue() == null || recordValue.getValue().toString().isBlank()) {
                    return;
                }

                RecordValue recordValueNew = new RecordValue();
                recordValueNew.setTenantId(formRecord.getTenantId());
                recordValueNew.setFormRecord(formRecord);
                FormHasField formHasField = formHasFields.stream()
                        .filter(fhField -> fhField.getFormField().getKey().equals(recordValue.getKey()))
                        .findFirst()
                        .orElseThrow(() -> new GearsException(GearsResponseStatus.NOT_FOUND, "Form field not found for given key " + recordValue.getKey()));
                recordValueNew.setFormHasField(formHasField);

                if (formHasField.getFormField().getDataType() == FormDataType.NUMBER_DECIMAL || formHasField.getFormField().getDataType() == FormDataType.NUMBER_INTEGER) {
                    recordValueNew.setNumberValue(Double.parseDouble(recordValue.getValue().toString()));
                } else {
                    recordValueNew.setTextValue(recordValue.getValue().toString());
                }

                formRecord.getValues().add(recordValueNew);
            }
        });
    }


    protected FormRecord createFormRecord(CustomFormSupportedEntity formSupportedEntity, List<SimpleFieldValueDto> customFields, Long tenantId) {
        Form form = getForm(formSupportedEntity);

        FormRecord formRecord = new FormRecord();
        formRecord.setAddedDate(new Date());
        formRecord.setForm(form);
        formRecord.setTenantId(tenantId);

        List<FormHasField> formHasFields = formHasFieldRepository.findAll(Specification.where(FormHasFieldSpecification.withFormId(form.getId())));

        formRecord.setValues(MiscUtils.getEmptyIfNull(customFields).stream()
                .filter(simpleFieldValueDto -> simpleFieldValueDto.getValue() != null && !simpleFieldValueDto.getValue().toString().isBlank())
                .map(simpleFieldValueDto -> {
                    RecordValue recordValue = new RecordValue();
                    recordValue.setTenantId(tenantId);
                    recordValue.setFormRecord(formRecord);
                    FormHasField formHasField = formHasFields.stream()
                            .filter(fhField -> fhField.getFormField().getKey().equals(simpleFieldValueDto.getKey()))
                            .findFirst()
                            .orElseThrow(() -> new GearsException(GearsResponseStatus.NOT_FOUND, "Form field not found for given key " + simpleFieldValueDto.getKey()));
                    recordValue.setFormHasField(formHasField);

                    if (formHasField.getFormField().getDataType() == FormDataType.NUMBER_DECIMAL || formHasField.getFormField().getDataType() == FormDataType.NUMBER_INTEGER) {
                        recordValue.setNumberValue(Double.parseDouble(simpleFieldValueDto.getValue().toString()));
                    } else {
                        recordValue.setTextValue(simpleFieldValueDto.getValue().toString());
                    }
                    return recordValue;
                }).collect(Collectors.toList()));

        return formRecord;
    }
}
