package com.ubs.sis.admin.master.dto.filter;

import com.ubs.commons.dto.filter.BaseFilterDto;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAssignmentFilterDto extends BaseFilterDto {
    @ApiParam(value = "Base assignment for filtering", defaultValue = "null")
    protected Long contextAssignmentId = null;
}
