package com.ubs.sis.admin.master.dto.filter;

import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EntityAssignmentFilterDto extends BaseAssignmentFilterDto {
    private BusinessEntity baseEntity;//what you want get
    private BusinessEntity assignedEntity;// parent
    private Long parentAssignmentId;// parent ID
    private Long structureMasterId;// base structure master ID
    private String name; // base structure master name
    private String code;// base structure master code
    private String shortName;// base structure short name
    private Boolean activeStatus;// base structure active status

    public EntityAssignmentFilterDto(EntityAssignmentChainFilterDto filterDto) {
        setBaseEntity(filterDto.getBaseEntity());
        setName(filterDto.getName());
        setCode(filterDto.getCode());
        setShortName(filterDto.getShortName());
        setActiveStatus(filterDto.getActiveStatus());
        setStructureMasterId(filterDto.getStructureMasterId());
        setSortBy(filterDto.getSortBy());
        setPageSize(filterDto.getPageSize());
        setContextAssignmentId(filterDto.getContextAssignmentId());
        setPageNo(filterDto.getPageNo());
        setSearchQuery(filterDto.getSearchQuery());
        setTenantId(filterDto.getTenantId());
        setSortDirection(filterDto.getSortDirection());
    }

    @Override
    public Integer getPageSize() {
        if (pageSize == null) {
            return Integer.MAX_VALUE;
        }
        return pageSize;
    }
}
