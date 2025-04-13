package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.RoleDto;
import com.codingplatform.coding_platform_backend.models.Role;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role role){
        return new RoleDto(role.getId(), role.getName());
    }

    public static Set<RoleDto> mapToRoleDtoList(Set<Role> roles){
        return roles.stream().map(RoleMapper::mapToRoleDto).collect(Collectors.toSet());
    }
}
