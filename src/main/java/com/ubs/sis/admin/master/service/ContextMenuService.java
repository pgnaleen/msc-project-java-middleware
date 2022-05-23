package com.ubs.sis.admin.master.service;

import com.ubs.commons.dto.response.Response;
import com.ubs.sis.admin.auth.domain.User;
import com.ubs.sis.admin.master.dto.response.ContextMenuResponse;

public interface ContextMenuService {

    Response<ContextMenuResponse> getContextMenuForUser(User user);
}
