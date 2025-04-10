package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProblemDto {
    private Long id;
    private String title;
    private String description;
    private Difficulty difficulty;
    private LocalDateTime creationDate;
    private List<Long> tagIds;
}
