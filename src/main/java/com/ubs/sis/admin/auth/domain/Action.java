package com.ubs.sis.admin.auth.domain;

import com.ubs.commons.domain.BaseEntity;

import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = Action.TABLE)
@SQLDelete(sql = "UPDATE " + Action.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class Action extends BaseEntity {

    public static final String TABLE = Globals.TABLE_PREFIX + "auth_action";

    public static final String COL_CODE = "code";
    public static final String COL_NAME = "name";

    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";

    @Column(name = COL_CODE, nullable = false)
    private String code;

    @Column(name = COL_NAME, nullable = false)
    private String name;
}