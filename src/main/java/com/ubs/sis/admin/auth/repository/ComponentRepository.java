package com.ubs.sis.admin.auth.repository;

import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.auth.domain.Component;

import java.util.List;

public interface ComponentRepository extends BaseRepository<Component> {

    List<Component> findByParent(Component component);

    boolean existsByCode(String code);
}