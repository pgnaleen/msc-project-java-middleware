package com.ubs.sis.admin.master.dto.request;

import com.ubs.commons.dto.request.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityAssignmentRequest extends BaseAssignedRequestDto {
    private Long assignmentParentId;// Entity Assignment ID
    private Long assignedEntityId;//Structure Master ID
    private Boolean activeState=true;

    private LinkedHashMap<String, String> additionalData;

    public EntityAssignmentRequest(Long entityAssignmentId,Boolean activeState) {
        this.id=entityAssignmentId;
        this.activeState = activeState;
    }

    public Boolean getActiveState() {
        if (activeState == null) {
            return true;
        }
        return activeState;
    }
}
