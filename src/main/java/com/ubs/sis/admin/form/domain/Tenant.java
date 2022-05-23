package com.ubs.sis.admin.form.domain;

import com.ubs.commons.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = Tenant.TABLE)
@SQLDelete(sql = "UPDATE " + Tenant.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class Tenant extends BaseEntity {


    public static final String TABLE = "tenant";


}
