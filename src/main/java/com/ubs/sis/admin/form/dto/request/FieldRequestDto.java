package com.ubs.sis.admin.form.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class FieldRequestDto extends BaseRequestDto {

    private String key;
    private String label;
    private FormDataType dataType;
    private Integer sortOrder;
    private ValidationDto validations;

    private Boolean isActive;
}
