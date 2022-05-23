package com.ubs.sis.admin.form.domain.custom_form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.form.domain.enums.FormDataType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = FormField.TABLE)
@SQLDelete(sql = "UPDATE " + FormField.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class FormField extends BaseEntity {

    public static final String TABLE = "form_field";

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 32)
    private FormDataType dataType;

    private Boolean isActive;

    private Boolean isCustom;

    private Boolean isInternal;
    private String key;

    @JoinColumn(name = "validation_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FieldValidation validations;

    @JoinColumn(name = "display_parameters_id", referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FormDisplayParameters displayParameters;
}
