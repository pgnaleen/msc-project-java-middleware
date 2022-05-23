package com.ubs.sis.admin.master.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class MasterEntity extends BaseEntity {
    private static final String COL_STRUCTURE_MASTER = "structure_master";

    @JoinColumn(name = COL_STRUCTURE_MASTER, referencedColumnName = BaseEntity.COL_ID)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StructureMaster structureMaster;

    @JsonIgnore
    public List<String> getSearchableCols() {
        return new ArrayList<>();
    }

    @JsonIgnore
    public abstract String getTableName();
}
