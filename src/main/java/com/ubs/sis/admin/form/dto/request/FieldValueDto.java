package com.ubs.sis.admin.form.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.request.BaseRequestDto;
import com.ubs.sis.admin.form.domain.enums.FormDataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FieldValueDto extends BaseRequestDto {

    public FieldValueDto(Long id, FormDataType dataType, boolean custom, ValidationDto validations, String key, Object value) {
        this.id = id;
        this.dataType = dataType;
        this.custom = custom;
        this.validations = validations;
        this.key = key;
        this.value = value;
    }

    private FormDataType dataType;
    private Boolean custom;
    private ValidationDto validations;
    private String key;
    private Object value;
}
