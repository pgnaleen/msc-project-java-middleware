package com.ubs.sis.admin.form.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.form.domain.enums.FormDataType;
import com.ubs.sis.admin.form.dto.request.ValidationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FieldResponseDto extends BaseResponseDto {

    private String key;
    private String label;
    private FormDataType dataType;
    private Integer sortOrder;
    private ValidationDto validations;
    private FormDisplayParametersResponseDto displayParameters;

    private Boolean isActive;

    private Boolean isCustom;
}
