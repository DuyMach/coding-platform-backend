package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.RoleName;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private RoleName name;
}
