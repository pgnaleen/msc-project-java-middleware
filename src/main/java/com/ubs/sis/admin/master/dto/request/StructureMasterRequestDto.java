package com.ubs.sis.admin.master.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StructureMasterRequestDto extends BaseAssignedRequestDto {
    private BusinessEntity businessEntity;
    private String name;
    private String name2L;
    private String code;
    private String shortName;
    private boolean activeStatus;

    public StructureMasterRequestDto(Long tenantId, BusinessEntity businessEntity, String name,String name2L, String code, String shortName, boolean activeStatus) {
        this.name2L = name2L;
        setTenantId(tenantId);
        this.businessEntity = businessEntity;
        this.name = name;
        this.code = code;
        this.shortName = shortName;
        this.activeStatus = activeStatus;
    }
    //    String name, String code,
//                                         String shortName,
}
