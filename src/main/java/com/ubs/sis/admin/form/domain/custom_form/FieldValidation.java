package com.ubs.sis.admin.form.domain.custom_form;

import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = FieldValidation.TABLE)
@SQLDelete(sql = "UPDATE " + FieldValidation.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
@NoArgsConstructor
public class FieldValidation extends BaseEntity {

    public static final String TABLE = "field_validation";

    private Boolean isRequired;
    private Long maxLength;
    private Long minLength;
    @ElementCollection
    private List<String> allowedExtensions;
    private Integer minWidth;
    private Integer maxWidth;
    private Integer minHeight;
    private Integer maxHeight;
}
