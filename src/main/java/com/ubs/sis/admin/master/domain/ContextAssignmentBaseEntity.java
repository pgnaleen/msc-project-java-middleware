package com.ubs.sis.admin.master.domain;

import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public class ContextAssignmentBaseEntity extends BaseEntity {
    public static final String COL_ENTITY_ASSIGNMENT = "assignment";

    @JoinColumn(name = COL_ENTITY_ASSIGNMENT, referencedColumnName = BaseEntity.COL_ID, updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private EntityAssignment entityAssignment;

    @Column(name = COL_ENTITY_ASSIGNMENT)
    private Long entityAssignmentId;
}
