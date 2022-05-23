package com.ubs.sis.admin.form.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.request.BaseRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleFieldValueDto extends BaseRequestDto {

    public SimpleFieldValueDto(Long id, String key, Object value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    private String key;
    private Object value;
}
