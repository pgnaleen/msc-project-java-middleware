package com.ubs.sis.admin.master.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ubs.commons.dto.request.BaseRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAssignedRequestDto extends BaseRequestDto {
    private Long contextAssignmentId;
    @JsonIgnore
    private boolean activeStatusChanged=false;
}
