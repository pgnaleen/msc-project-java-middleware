package com.ubs.sis.admin.form.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.form.dto.request.SimpleFieldValueDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormResponseDto extends BaseResponseDto {

    private List<SimpleFieldValueDto> customFields;
}
