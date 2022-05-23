package com.ubs.sis.admin.master.dto.response;

import com.ubs.commons.dto.response.DeleteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityAssignmentBulkResponseDto extends DataResponseDto {
    private List<EntityAssignmentResponseDto> entityAssignmentResponseDtos;
    private List<DeleteResponseDto> deleteResponseDtos;


}
