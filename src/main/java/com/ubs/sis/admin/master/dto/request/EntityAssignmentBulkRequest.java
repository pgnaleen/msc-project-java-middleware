package com.ubs.sis.admin.master.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class EntityAssignmentBulkRequest {
    private List<EntityAssignmentRequest> assignmentRequests;
    private List<Long> unAssignRequests;
    private Long contextAssignmentId;
}
