package com.ubs.sis.admin.auth.service.facade;

import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.auth.dto.request.UserRequestDto;

public interface UserServiceFacade {
    public String createUser(UserRequestDto userRequestDto);
    public String updateUser(User user);
    public String deleteUser(User user);
}
