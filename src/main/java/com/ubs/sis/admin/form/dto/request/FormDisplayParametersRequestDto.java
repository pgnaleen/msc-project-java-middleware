package com.ubs.sis.admin.form.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.request.BaseRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDisplayParametersRequestDto extends BaseRequestDto {
    private Float tableColumnWeight;
    private Boolean isVisibleInTable;
}
