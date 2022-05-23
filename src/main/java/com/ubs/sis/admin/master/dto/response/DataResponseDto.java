package com.ubs.sis.admin.master.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ubs.commons.dto.response.BaseResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponseDto extends BaseResponseDto {
    private BaseResponseDto data;
    @JsonIgnore
    private Long referenceId;

}
