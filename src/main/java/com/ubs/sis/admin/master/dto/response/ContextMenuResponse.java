package com.ubs.sis.admin.master.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ContextMenuResponse {
    private List<ContextMenuItem> menuItems;

    @AllArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class ContextMenuItem extends BaseResponseDto {
        private Long structureMasterId;
        private String logoURL;
        private String name;
        private String code;
        private BusinessEntity businessEntity;
        private Boolean isDefault;
        private Boolean activeStatus;
        private List<ContextSubMenuItem> children;

        public ContextMenuItem(Long id, Long structureMasterId, String logoURL,
                               String name,
                               String code,
                               List<ContextSubMenuItem> children, BusinessEntity businessEntity) {
            setId(id);

            this.structureMasterId = structureMasterId;
            this.logoURL = logoURL;
            this.name = name;
            setCode(code);
            this.children = children;
            this.businessEntity = businessEntity;
        }

        public ContextMenuItem(Long id, Long structureMasterId, String logoURL,
                               String name,
                               String code,
                               List<ContextSubMenuItem> children, BusinessEntity businessEntity,
                               Boolean isDefault) {
            this(id, structureMasterId, logoURL, name, code, children, businessEntity);
            setIsDefault(isDefault);
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class ContextSubMenuItem extends BaseResponseDto {
        private Long structureMasterId;
        private String logoURL;
        private String name;
        private String code;
        private BusinessEntity businessEntity;
        private Boolean isDefault;
        private Boolean activeStatus;

        public ContextSubMenuItem(Long id, Long structureMasterId, String logoURL, String name,
                                  String code,
                                  BusinessEntity businessEntity) {
            setId(id);
            this.structureMasterId = structureMasterId;
            this.logoURL = logoURL;
            this.name = name;
            this.code = code;
            this.businessEntity = businessEntity;
        }

        public ContextSubMenuItem(Long id, Long structureMasterId, String logoURL, String name,
                                  String code,
                                  BusinessEntity businessEntity, Boolean isDefault) {
            this(id, structureMasterId, logoURL, name, code, businessEntity);
            setIsDefault(isDefault);
        }
    }
}
