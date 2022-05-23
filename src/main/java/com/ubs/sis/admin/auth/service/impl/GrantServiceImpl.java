package com.ubs.sis.admin.auth.service.impl;

import com.ubs.sis.admin.auth.domain.Grant;
import com.ubs.sis.admin.auth.repository.GrantRepository;
import com.ubs.sis.admin.auth.service.GrantService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GrantServiceImpl implements GrantService {

    private final GrantRepository grantRepository;

    public GrantServiceImpl(GrantRepository grantRepository) {
        this.grantRepository = grantRepository;
    }

    @Override
    public void deleteAll(Set<Grant> grants) {
        grantRepository.deleteAll(grants);
    }
}
