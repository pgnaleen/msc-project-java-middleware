package com.ubs.sis.admin.form.domain.custom_form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = FormHasField.TABLE)
@SQLDelete(sql = "UPDATE " + FormHasField.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class FormHasField extends BaseEntity {

    public static final String TABLE = "form_has_field";

    @JoinColumn(name = "form_id", referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Form form;

    @JoinColumn(name = "form_field_id", referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private FormField formField;

    @JoinColumn(name = "validation_id", referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FieldValidation fieldValidation;

    private Boolean isActive;

    private Boolean isCustom;

    private Integer sortOrder = 0;
    private String label;

    @JoinColumn(name = "display_parameters_id", referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FormDisplayParameters displayParameters;

    public FieldValidation getFieldValidation() {
        if (fieldValidation == null) {
            return getFormField().getValidations();
        }
        return fieldValidation;
    }
}
