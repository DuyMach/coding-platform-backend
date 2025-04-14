package com.codingplatform.coding_platform_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AddTagsToProblemDto {
    private Set<Long> tagIdSet;
}
