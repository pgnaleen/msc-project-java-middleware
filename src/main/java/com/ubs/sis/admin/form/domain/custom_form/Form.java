package com.ubs.sis.admin.form.domain.custom_form;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import com.ubs.sis.admin.master.domain.enums.Group;
import com.ubs.sis.admin.form.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = Form.TABLE)
@SQLDelete(sql = "UPDATE " + Form.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class Form extends BaseEntity {


    public static final String TABLE = "form";

    private String name;

    private Status status;

    @Enumerated(EnumType.STRING)
    private CustomFormSupportedEntity descriptor;

    @Enumerated(EnumType.STRING)
    @Column(name="group_name")
    private Group group;

    @JoinColumn(name = "form_definition_id", referencedColumnName = "id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FormDefinition formDefinition;


    @Column(name = "form_definition_id")
    private Long formDefinitionId;

}
