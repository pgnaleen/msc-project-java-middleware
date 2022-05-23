package com.ubs.sis.admin.form.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.form.domain.custom_form.Form;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import com.ubs.sis.admin.form.domain.enums.Status;
import com.ubs.sis.admin.util.MiscUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormStructureResponseDto extends BaseResponseDto {
    private String name;
    private CustomFormSupportedEntity descriptor;
    private String group;
    private Status status;
    List<FormHeaderDto> headers;

    public void setFormBasicData(Form form) {
        this.id = form.getId();
        this.name = form.getName();
        this.descriptor = form.getDescriptor();
        this.group = form.getGroup().getGroupName();
        this.status = form.getStatus();
    }

    public void setProductHeaders(List<FormHeaderDto> productHeaders) {
        List<FormHeaderDto> headers = new ArrayList<>();
        headers.addAll(MiscUtils.getEmptyIfNull(productHeaders));
        headers.addAll(MiscUtils.getEmptyIfNull(getHeaders()));
        setHeaders(headers);
    }
}
