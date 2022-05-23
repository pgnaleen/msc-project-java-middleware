package com.ubs.sis.admin.auth.domain;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = Role.TABLE)
@SQLDelete(sql = "UPDATE " + Role.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class Role extends BaseEntity {

    public static final String TABLE = Globals.TABLE_PREFIX + "auth_role";

    public static final String COL_NAME = "name";
    public static final String COL_CODE = "code";
    public static final String COL_ROLE_ID = "role_id";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_GRANTS = "grants";

    @Column(name = COL_NAME, nullable = false)
    private String name;

    @Column(name = COL_CODE, nullable = false)
    private String code;

    @JoinColumn(name = COL_ROLE_ID, nullable = false)
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Grant> grants;
}