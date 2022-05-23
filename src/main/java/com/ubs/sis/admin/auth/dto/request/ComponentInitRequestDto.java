package com.ubs.sis.admin.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.commons.dto.request.BaseRequestDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentInitRequestDto extends BaseRequestDto {
//    @NotNull(message = "Code is required")
    private String code;

//    @NotNull(message = "Name is required")
    private String name;

    private List<@Valid ComponentInitRequestDto> children;

    private Set<Long> actionIds;
}