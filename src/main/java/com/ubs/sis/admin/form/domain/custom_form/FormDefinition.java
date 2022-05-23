package com.ubs.sis.admin.form.domain.custom_form;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = FormDefinition.TABLE, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"business_entity", "assigned_business_entity", "deleted"})
}
)
@SQLDelete(sql = "UPDATE " + FormDefinition.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class FormDefinition extends BaseEntity {

    public static final String TABLE = "form_definition";

    @Enumerated(EnumType.STRING)
    @Column(name = "business_entity", length = 20)
    private BusinessEntity businessEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_business_entity", length = 20)
    private BusinessEntity assignedBusinessEntity;

    private String name;
    private String description;
    private Boolean multiple;
    private Boolean isDefault;


}
