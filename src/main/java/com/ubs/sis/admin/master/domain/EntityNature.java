package com.ubs.sis.admin.master.domain;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = EntityNature.TABLE)
@SQLDelete(sql = "UPDATE " + EntityNature.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class EntityNature extends BaseEntity{
    public static final String TABLE = Globals.TABLE_PREFIX + "entity_nature";



}
