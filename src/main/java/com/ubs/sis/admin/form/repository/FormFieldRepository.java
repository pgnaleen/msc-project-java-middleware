package com.ubs.sis.admin.form.repository;

import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.form.domain.custom_form.FormField;

public interface FormFieldRepository extends BaseRepository<FormField> {

    FormField findByKey(String key);
}
