package com.ubs.sis.admin.form.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ubs.sis.admin.form.domain.custom_form.FieldValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationDto {
    private Boolean required;
    private Long minLength;
    private Long maxLength;
    private List<String> allowedExtensions;
    private Integer minWidth;
    private Integer maxWidth;
    private Integer minHeight;
    private Integer maxHeight;

    public ValidationDto(FieldValidation fieldValidation) {
        if (fieldValidation != null) {
            setMinLength(fieldValidation.getMinLength());
            setMaxLength(fieldValidation.getMaxLength());
            setRequired(fieldValidation.getIsRequired());
            setAllowedExtensions(fieldValidation.getAllowedExtensions());
            setMinWidth(fieldValidation.getMinWidth());
            setMaxWidth(fieldValidation.getMaxWidth());
            setMinHeight(fieldValidation.getMinHeight());
            setMaxHeight(fieldValidation.getMaxHeight());
        }
    }
}
