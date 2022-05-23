package com.ubs.sis.admin.auth.dto.filter;

import com.ubs.commons.dto.filter.BaseFilterDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComponentFilterDto extends BaseFilterDto {
    private Long parentId;

    private String code;

    private String name;
}