package com.ubs.sis.admin.master.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityAssignmentChainResponseDto extends DataResponseDto {
    private BusinessEntity businessEntity;
    private BusinessEntity parentBusinessEntity;
    private EntityAssignmentChainResponseDto parentData;
    private Long parentAssignmentId;
    @JsonIgnore
    private Long parentReferenceId;
    private Boolean activeStatus;

}
