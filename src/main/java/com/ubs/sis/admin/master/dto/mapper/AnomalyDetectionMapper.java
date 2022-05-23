package com.ubs.sis.admin.master.dto.mapper;

import com.ubs.sis.admin.master.domain.AnomalyDetection;
import com.ubs.sis.admin.master.dto.request.AnomalyDetectionRequestDto;
import com.ubs.sis.admin.master.dto.response.AnomalyDetectionResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnomalyDetectionMapper extends BaseMasterMapper<AnomalyDetection, AnomalyDetectionRequestDto, AnomalyDetectionResponseDto>{
}
