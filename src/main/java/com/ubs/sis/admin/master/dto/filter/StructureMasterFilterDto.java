package com.ubs.sis.admin.master.dto.filter;

import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StructureMasterFilterDto extends BaseAssignmentFilterDto {
    private BusinessEntity businessEntity;
    private Boolean withData = false;
    private String name;
    private String code;
    private String shortName;
    private boolean activeStatus;

    @Override
    public Integer getPageSize() {
        if (pageSize == null) {
            return Integer.MAX_VALUE;
        }
        return pageSize;
    }
}
