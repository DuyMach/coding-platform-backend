package com.codingplatform.coding_platform_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class AddTagsToProblemDto {
    private Set<Long> tagIdSet;
}
