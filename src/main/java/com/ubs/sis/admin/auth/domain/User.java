package com.ubs.sis.admin.auth.domain;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = User.TABLE)
@SQLDelete(sql = "UPDATE " + User.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class User extends BaseEntity {

    public static final String TABLE = Globals.TABLE_PREFIX + "auth_user";

    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_CODE = "code";
    public static final String COL_ROLE_ID = "role_id";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_DOB = "dob";
    public static final String COL_PHONE_NO = "phone_no";

    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_ROLE = "role";
    public static final String FIELD_FIRST_NAME = "first_name";
    public static final String FIELD_LAST_NAME = "last_name";
    public static final String FIELD_DOB = "dob";
    public static final String FIELD_PHONE_NO = "phone_no";

    @Column(name = COL_USERNAME, nullable = false)
    private String username;

    @Column(name = COL_PASSWORD, insertable = false, updatable = false)
    private String password;

    @Column(name = COL_EMAIL)
    private String email;

    @JoinColumn(name = COL_ROLE_ID)
    @ManyToOne(cascade = CascadeType.MERGE)
    private Role role;

    @Column(name = COL_FIRST_NAME)
    private String firstName;

    @Column(name = COL_LAST_NAME)
    private String lastName;

    @Column(name = COL_DOB)
    private String dob;

    @Column(name = COL_PHONE_NO)
    private String phoneNo;
}
