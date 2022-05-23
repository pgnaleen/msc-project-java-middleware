package com.ubs.sis.admin.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = Component.TABLE)
@SQLDelete(sql = "UPDATE " + Component.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class Component extends BaseEntity {

    public static final String TABLE = Globals.TABLE_PREFIX + "auth_component";
    public static final String TABLE_COMPONENT_ACTION = Globals.TABLE_PREFIX + "auth_component_action";

    public static final String COL_PARENT_ID = "parent_id";
    public static final String COL_CODE = "code";
    public static final String COL_NAME = "name";
    public static final String COL_ACTION_ID = "action_id";
    public static final String COL_COMPONENT_ID = "component_id";

    public static final String FIELD_PARENT = "parent";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ACTIONS = "actions";

    @JsonIgnore
    @JoinColumn(name = COL_PARENT_ID)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Component parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = FIELD_PARENT, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Component> children;

    @JoinTable(name = TABLE_COMPONENT_ACTION,
            joinColumns = @JoinColumn(name = COL_COMPONENT_ID),
            inverseJoinColumns = @JoinColumn(name = COL_ACTION_ID))
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Action> actions;

    @Column(name = COL_CODE, nullable = false)
    private String code;

    @Column(name = COL_NAME, nullable = false)
    private String name;
}