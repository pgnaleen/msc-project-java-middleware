package com.ubs.sis.admin.form.domain;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.form.domain.custom_form.FormRecord;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Setter
@Getter
public abstract class SupportCustomForm extends BaseEntity {

    @JoinColumn(name = "record_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FormRecord formRecord;

}
