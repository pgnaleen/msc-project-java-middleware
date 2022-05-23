package com.ubs.sis.admin.master.domain.enums;

import com.ubs.sis.admin.master.domain.*;
import com.ubs.sis.admin.master.service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum BusinessEntity {
    AD(BusinessDomain.MACHINE_LEARNING, AnomalyDetection.class, AnomalyDetectionService.class);

    private BusinessDomain domain;
    private Class<? extends MasterEntity> masterEntityClass;
    private Class<? extends MasterBaseService> serviceClass;
    private boolean root;

    BusinessEntity(BusinessDomain domain, Class<? extends MasterEntity> masterEntityClass,
                   Class<? extends MasterBaseService> serviceClass) {
        this.domain = domain;
        this.masterEntityClass = masterEntityClass;
        this.serviceClass = serviceClass;
        root = false;
    }


}
