package com.ubs.sis.admin.form.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubs.commons.dto.filter.BaseFilterDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FieldFilterDto extends BaseFilterDto {

    @JsonProperty("custom")
    private Boolean isCustom;
}
