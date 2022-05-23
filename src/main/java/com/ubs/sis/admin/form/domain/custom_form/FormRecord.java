package com.ubs.sis.admin.form.domain.custom_form;

import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = FormRecord.TABLE)
@SQLDelete(sql = "UPDATE " + FormRecord.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class FormRecord extends BaseEntity {


    public static final String TABLE = "form_record";
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Form form;

    private Date addedDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy  = "formRecord", cascade = CascadeType.ALL)
    private List<RecordValue> values;
}
