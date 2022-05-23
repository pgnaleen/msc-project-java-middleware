package com.ubs.sis.admin.auth.dto.filter;

import com.ubs.commons.dto.filter.BaseFilterDto;
import com.ubs.sis.admin.auth.dto.request.GrantRequestDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RoleFilterDto extends BaseFilterDto {
    private String name;
    private String code;
    private List<GrantRequestDto> grants;
}