package com.ubs.sis.admin.master.domain.lookup;

import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseLookupEntity extends BaseEntity {

    private static final String COL_NAME = "name";
    private static final String COL_NAME_2L = "name_2l";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_NAME_2L = "name2L";

    @Column(name = COL_NAME, length = 60)
    private String name;
    @Column(name = COL_NAME_2L, length = 60)
    private String name2L;
}
