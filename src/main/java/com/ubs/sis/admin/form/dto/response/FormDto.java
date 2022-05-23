package com.ubs.sis.admin.form.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import com.ubs.sis.admin.form.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDto extends BaseResponseDto {

    public FormDto(Long id, String name, CustomFormSupportedEntity descriptor, String group, Status status) {
        this.id = id;
        this.name = name;
        this.descriptor = descriptor;
        this.group = group;
        this.status = status;
    }

    private String name;
    private CustomFormSupportedEntity descriptor;
    private String group;
    private Status status;
}
