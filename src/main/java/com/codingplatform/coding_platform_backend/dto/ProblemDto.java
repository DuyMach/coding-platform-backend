package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.ProblemVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@Builder
public class ProblemDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    private LocalDateTime creationDate;

    @NotBlank(message = "Function name is required")
    private String functionName;

    private List<String> constraints;

    private List<String> hints;

    private boolean isPremium;

    private LocalDateTime updatedAt;

    @NotNull(message = "Visibility is required")
    private ProblemVisibility visibility;
}
