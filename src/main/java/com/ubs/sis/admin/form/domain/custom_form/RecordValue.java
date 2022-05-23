package com.ubs.sis.admin.form.domain.custom_form;

import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = RecordValue.TABLE)
@SQLDelete(sql = "UPDATE " + RecordValue.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class RecordValue extends BaseEntity {

    public static final String TABLE = "record_value";

    @JoinColumn(name = "form_record_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FormRecord formRecord;

    @JoinColumn(name = "form_has_field_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FormHasField formHasField;

    private Double numberValue;

    private String textValue;

    public String getValue(){
        if (numberValue != null) {
            return String.valueOf(numberValue);
        }
        if (textValue != null) {
            return getTextValue();
        }
        return "N/A";
    }

}
