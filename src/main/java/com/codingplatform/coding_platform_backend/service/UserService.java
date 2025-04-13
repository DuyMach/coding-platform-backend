package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.RoleDto;
import com.codingplatform.coding_platform_backend.dto.UserEntityDto;
import com.codingplatform.coding_platform_backend.models.UserEntity;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<UserEntityDto> getAllUser();
    Set<RoleDto> getRolesByUserId(Long userId);
    String addRoleToUser(Long userId, Long roleId);
}
