package com.ubs.sis.admin.master.domain.lookup;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = Country.TABLE)
@SQLDelete(sql = "UPDATE " + Country.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class Country extends BaseLookupEntity {
    public static final String TABLE = Globals.TABLE_PREFIX + "country";

    public static final String COL_COUNTRY_CODE = "countryCode";
    public static final String COL_COUNTRY_NAME = "countryName";
    public static final String COL_COUNTRY_ICON = "countryIcon";


    public static final String FIELD_COUNTRY_CODE = "countryCode";
    public static final String FIELD_COUNTRY_NAME = "countryName";
    public static final String FIELD_COUNTRY_ICON = "countryIcon";


    @Column(name = COL_COUNTRY_CODE, length = 5)
    private String countryCode;

    @Column(name = COL_COUNTRY_ICON, length = 50)
    private String countryIcon;
}
