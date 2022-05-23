package com.ubs.sis.admin.master.domain.enums;

import com.ubs.sis.admin.form.domain.enums.FormDataType;
import com.ubs.sis.admin.form.dto.FormDefinitionDescriptor;
import com.ubs.sis.admin.form.dto.response.FormHeaderDto;
import com.ubs.sis.admin.master.domain.*;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum CustomFormSupportedEntity {

    ANOMALY_DETECTION_FORM(new FormDefinitionDescriptor(BusinessEntity.AD, null),
            Group.ANOMALY_DETECTION_GROUP,
            Arrays.asList(
                    new FormHeaderDto(AnomalyDetection.COL_DATASET_CODE, FormDataType.TEXT),
                    new FormHeaderDto(AnomalyDetection.COL_DATASET_LOCATION, FormDataType.TEXT)
            ));

    @Getter
    private final FormDefinitionDescriptor formDefinitionDescriptor;

    @Getter
    private final Group group;

    @Getter
    private final List<FormHeaderDto> headerDtos;

    CustomFormSupportedEntity(FormDefinitionDescriptor formDefinitionDescriptor, Group group,
                              List<FormHeaderDto> headerDtos) {
        this.formDefinitionDescriptor = formDefinitionDescriptor;
        this.group = group;
        this.headerDtos = headerDtos;
    }
}
