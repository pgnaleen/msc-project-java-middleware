package com.ubs.sis.admin.master.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnomalyDetectionFilterDto extends BaseAssignmentFilterDto {

    private String datasetCode;
    private String datasetLocation;
    private String accessLogLocation;
}
