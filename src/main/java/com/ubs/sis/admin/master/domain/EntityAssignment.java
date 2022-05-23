package com.ubs.sis.admin.master.domain;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = EntityAssignment.TABLE)
@SQLDelete(sql = "UPDATE " + EntityAssignment.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class EntityAssignment extends ContextAssignmentBaseEntity {

    public static final String TABLE = Globals.TABLE_PREFIX + "entity_assignment";

    public static final String COL_ENTITY = "main_entity";
    public static final String COL_ASSIGN_ENTITY = "parent_id";
    public static final String COL_REFERENCE_ID = "reference_id";
    public static final String COL_BASE_BUSINESS_ENTITY = "base_business_entity";
    public static final String COL_ASSIGNED_BUSINESS_ENTITY = "assigned_business_entity";

    public static final String COL_ACTIVE_STATUS = "active_status";
    public static final String FIELD_ENTITY = "entity";
    public static final String FIELD_BASE_BUSINESS_ENTITY = "baseBusinessEntity";
    public static final String FIELD_ASSIGNED_BUSINESS_ENTITY = "assignedBusinessEntity";

    @JoinColumn(name = COL_ENTITY, referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private StructureMaster entity;


    @JoinColumn(name = COL_ASSIGN_ENTITY, referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private EntityAssignment parent;

    @Column(name = COL_REFERENCE_ID)
    private Long referenceId;

    @Column(name = COL_ACTIVE_STATUS)
    private Boolean activeStatus;
    @Enumerated(EnumType.STRING)

    @Column(name = COL_BASE_BUSINESS_ENTITY)
    private BusinessEntity baseBusinessEntity;
    @Enumerated(EnumType.STRING)

    @Column(name = COL_ASSIGNED_BUSINESS_ENTITY)
    private BusinessEntity assignedBusinessEntity;


    public Boolean getActiveStatus() {
        if (activeStatus == null) {
            return true;
        }
        return activeStatus;
    }

    public void setEntity(StructureMaster entity) {
        this.entity = entity;
        if (entity != null) {
            setBaseBusinessEntity(entity.getBusinessEntity());
        }
    }

    public void setParent(EntityAssignment parent) {
        this.parent = parent;
        if (parent != null) {
            setAssignedBusinessEntity(parent.getBaseBusinessEntity());
        }
    }
}
