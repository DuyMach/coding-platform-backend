package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ProblemDto {
    private Long id;
    private String title;
    private String description;
    private Difficulty difficulty;
    private LocalDateTime creationDate;
}
