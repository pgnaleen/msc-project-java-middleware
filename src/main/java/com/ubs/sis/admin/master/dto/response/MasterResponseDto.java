package com.ubs.sis.admin.master.dto.response;

import com.ubs.commons.dto.response.BaseResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MasterResponseDto extends BaseResponseDto {
    private Long structureMasterId;

}
