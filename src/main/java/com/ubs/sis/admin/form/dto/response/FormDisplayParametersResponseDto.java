package com.ubs.sis.admin.form.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.form.domain.custom_form.FormDisplayParameters;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDisplayParametersResponseDto extends BaseResponseDto {
    private Float tableColumnWeight;
    private Boolean isVisibleInTable;

    public static FormDisplayParametersResponseDto fromEntity(FormDisplayParameters parameters) {
        if (parameters == null) {
            return null;
        }

        FormDisplayParametersResponseDto dto = new FormDisplayParametersResponseDto();
        dto.setIsVisibleInTable(parameters.getIsVisibleInTable());
        dto.setTableColumnWeight(parameters.getTableColumnWeight());

        return dto;
    }
}
