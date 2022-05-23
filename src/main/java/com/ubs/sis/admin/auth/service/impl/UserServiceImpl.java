package com.ubs.sis.admin.auth.service.impl;

import com.ubs.commons.exception.GearsException;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.auth.dto.mapper.UserMapper;
import com.ubs.sis.admin.auth.dto.request.UserRequestDto;
import com.ubs.sis.admin.auth.dto.response.UserResponseDto;
import com.ubs.sis.admin.auth.repository.UserRepository;
import com.ubs.sis.admin.auth.service.RoleService;
import com.ubs.sis.admin.auth.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @Override
    public BaseRepository<User> getRepository() {
        return userRepository;
    }

    @Override
    public UserMapper getModelMapper() {
        return userMapper;
    }

    @Override
    public User validateAndMapToEntityForSave(UserRequestDto requestDto) {

        User user = userMapper.mapRequestDtoToEntity(requestDto);
        user.setRole(roleService.getRoleById(requestDto.getRoleId()));

        return user;
    }

    @Override
    public User validateAndMapToEntityForUpdate(UserRequestDto requestDto, User entity) {

        if (requestDto.getPassword() != null && !requestDto.getPassword().equals(""))
            entity.setPassword(requestDto.getPassword());
        if (requestDto.getEmail() != null && !requestDto.getEmail().equals(""))
            entity.setEmail(requestDto.getEmail());
        if (requestDto.getRoleId() != 0)
            entity.setRole(roleService.getRoleById(requestDto.getRoleId()));
        if (requestDto.getFirstName() != null && !requestDto.getFirstName().equals(""))
            entity.setFirstName(requestDto.getFirstName());
        if (requestDto.getLastName() != null && !requestDto.getLastName().equals(""))
            entity.setLastName(requestDto.getLastName());
        if (requestDto.getDob() != null && !requestDto.getDob().equals(""))
            entity.setDob(requestDto.getDob());
        if (requestDto.getPhoneNo() != null && !requestDto.getPhoneNo().equals(""))
            entity.setPhoneNo(requestDto.getPhoneNo());

        return entity;
    }

    @Override
    public User getEntityByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GearsException(GearsResponseStatus.NOT_FOUND, "Invalid username");
        }

        return user;
    }

    @Override
    public UserResponseDto getDtoByUsername(String username) {
        return userMapper.mapEntityToResponseDto(getEntityByUsername(username));
    }

    @Override
    public boolean isUserExist(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isEmailExist(String email) {
        List<User> users = null;
        try {
            users = userRepository.findByEmail(email);
        } catch (javax.persistence.NonUniqueResultException nurEx) { // when more than one email entries for same email
            return true;
        }

        if (users.isEmpty()) {
            return false;
        }

        return true;
    }
}
