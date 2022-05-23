package com.ubs.sis.admin.form.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FieldUpdateRequest {
    private List<FieldRequestDto> fields;
    private List<Long> removeIds;

}
