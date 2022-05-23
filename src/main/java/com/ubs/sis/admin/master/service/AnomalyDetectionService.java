package com.ubs.sis.admin.master.service;

import com.ubs.commons.dto.response.Response;
import com.ubs.sis.admin.form.dto.response.FormStructureResponseDto;
import com.ubs.sis.admin.master.domain.AnomalyDetection;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.master.dto.filter.AnomalyDetectionFilterDto;
import com.ubs.sis.admin.master.dto.request.AnomalyDetectionRequestDto;
import com.ubs.sis.admin.master.dto.response.AnomalyDetectionResponseDto;

public interface AnomalyDetectionService extends MasterBaseService<AnomalyDetection, AnomalyDetectionRequestDto,
        AnomalyDetectionResponseDto, AnomalyDetectionFilterDto> {

    Response<FormStructureResponseDto> getStructure();

    @Override
    default BusinessEntity getEntityType(){
        return BusinessEntity.AD;
    }
}
