package com.ubs.sis.admin.auth.repository;

import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.auth.domain.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);

    List<User> findByEmail(String email);
}
