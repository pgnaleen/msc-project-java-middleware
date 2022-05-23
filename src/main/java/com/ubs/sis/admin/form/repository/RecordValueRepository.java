package com.ubs.sis.admin.form.repository;

import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.form.domain.custom_form.RecordValue;

import java.util.List;

public interface RecordValueRepository extends BaseRepository<RecordValue> {

    List<RecordValue> findByFormHasFieldFormIdAndFormHasFieldId(Long formId, Long formFieldId);
}
