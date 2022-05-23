package com.ubs.sis.admin.auth.domain;

import com.ubs.commons.domain.BaseEntity;

import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = Grant.TABLE)
@SQLDelete(sql = "UPDATE " + Grant.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class Grant extends BaseEntity {

    public static final String TABLE = Globals.TABLE_PREFIX + "auth_grant";

    public static final String COL_COMPONENT_ID = "component_id";
    public static final String COL_ACTION_ID = "action_id";

    public static final String FIELD_COMPONENT = "component";
    public static final String FIELD_ACTION = "action";

    @JoinColumn(name = COL_COMPONENT_ID, nullable = false)
    @ManyToOne
    private Component component;

    @JoinColumn(name = COL_ACTION_ID, nullable = false)
    @ManyToOne
    private Action action;
}