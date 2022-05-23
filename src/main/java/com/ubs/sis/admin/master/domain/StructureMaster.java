package com.ubs.sis.admin.master.domain;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = StructureMaster.TABLE)
@SQLDelete(sql = "UPDATE " + StructureMaster.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class StructureMaster extends ContextAssignmentBaseEntity {

    public static final String TABLE = Globals.TABLE_PREFIX + "structure_master";

    public static final String COL_BUSINESS_ENTITY = "business_entity";
    public static final String COL_NAME = "name";
    public static final String COL_NAME_2L = "name2l";
    public static final String COL_SHORT_NAME = "short_name";
    public static final String COL_REFERENCE_ID = "reference_id";
    public static final String COL_CODE = "entity_code";
    public static final String COL_ACTIVE_STATUS = "activeStatus";

    public static final String FIELD_BUSINESS_ENTITY = "businessEntity";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_SHORT_NAME = "shortName";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_ACTIVE_STATUS = "activeStatus";

    @Enumerated(EnumType.STRING)
    @Column(name = COL_BUSINESS_ENTITY, nullable = false, length = 8)
    private BusinessEntity businessEntity;

    @Column(name = COL_NAME, nullable = false)
    private String name;

    @Column(name = COL_NAME_2L)
    private String name2L;

    @Column(name = COL_SHORT_NAME)
    private String shortName;

    @Column(name = COL_CODE)
    private String code;

    @Column(name = COL_REFERENCE_ID)
    private Long referenceId;

    @Column(name = COL_ACTIVE_STATUS)
    private boolean activeStatus;

    public StructureMaster(Long referenceId) {
        this.referenceId = referenceId;
    }
}
