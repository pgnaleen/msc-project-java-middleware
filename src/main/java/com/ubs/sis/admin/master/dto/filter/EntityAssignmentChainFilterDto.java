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
public class EntityAssignmentChainFilterDto extends BaseAssignmentFilterDto {

    private Integer depth = 1;
    private BusinessEntity baseEntity;//what you want get
    private BusinessEntity untilEntity;// parent
    private Long structureMasterId;// base structure master ID
    private String name; // base structure master name
    private String code;// base structure master code
    private String shortName;// base structure short name
    private Boolean activeStatus;// base structure active status

    @Override
    public Integer getPageSize() {
        if (pageSize == null) {
            return Integer.MAX_VALUE;
        }
        return pageSize;
    }

    public Integer getDepth() {
        if (depth == null) {
            return 1;
        }
        return depth;
    }
}
