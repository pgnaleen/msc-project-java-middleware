package com.ubs.sis.admin.master.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntityAssignmentResponseDto extends DataResponseDto {
    private BusinessEntity businessEntity;
    private BusinessEntity parentBusinessEntity;
    private BaseResponseDto parentData;
    private Boolean activeStatus;
    private Long parentAssignmentId;
    @JsonIgnore
    private Long parentReferenceId;
}
