package com.ubs.sis.admin.form.domain.custom_form;

import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = FormDisplayParameters.TABLE)
@SQLDelete(sql = "UPDATE " + FormDisplayParameters.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
@NoArgsConstructor
public class FormDisplayParameters extends BaseEntity {

    public static final String TABLE = "form_display_parameters";

    @ColumnDefault("1")
    @Column(nullable = false)
    private Float tableColumnWeight = 1f;

    private Boolean isVisibleInTable;
}
