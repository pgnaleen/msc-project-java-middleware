package com.ubs.sis.admin.auth.service;

import com.ubs.sis.admin.auth.domain.Grant;

import java.util.Set;

public interface GrantService {

    void deleteAll(Set<Grant> grants);
}