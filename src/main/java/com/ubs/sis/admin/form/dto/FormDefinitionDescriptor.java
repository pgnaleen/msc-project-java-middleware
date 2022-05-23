package com.ubs.sis.admin.form.dto;

import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class FormDefinitionDescriptor {
    private BusinessEntity businessEntity;
    private BusinessEntity assignedBusinessEntity;
}
