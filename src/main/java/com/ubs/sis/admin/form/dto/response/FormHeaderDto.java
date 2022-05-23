package com.ubs.sis.admin.form.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.form.domain.enums.FormDataType;
import com.ubs.sis.admin.form.dto.request.ValidationDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormHeaderDto extends BaseResponseDto {
    private Long formFieldId;
    private String key;
    private String label;
    private FormDataType dataType;
    private Integer sortOrder;

    @JsonProperty("custom")
    private Boolean isCustom;

    @JsonProperty("active")
    private Boolean isActive;

    private ValidationDto validations;
    private FormDisplayParametersResponseDto displayParameters;

    public FormHeaderDto(Long id, Long formFieldId, String key, String label, FormDataType dataType,
                         Integer sortOrder, boolean isCustom, Boolean isActive, ValidationDto validations,
                         FormDisplayParametersResponseDto displayParameters) {
        this.id = id;
        this.formFieldId = formFieldId;
        this.key = key;
        this.label = label;
        this.dataType = dataType;
        this.sortOrder = sortOrder;
        this.isCustom = isCustom;
        this.isActive = isActive;
        this.validations = validations;
        this.displayParameters = displayParameters;
    }

    public FormHeaderDto(String key, FormDataType dataType) {
        this.key = key;
        setIsCustom(false);
        setIsActive(true);
        setLabel(key);
        this.dataType = dataType;
    }
}
