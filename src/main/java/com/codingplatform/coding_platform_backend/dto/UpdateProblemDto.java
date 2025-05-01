package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.ProblemVisibility;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UpdateProblemDto {
    private String title;
    private String description;
    private Difficulty difficulty;
    private String functionName;
    private List<String> constraints;
    private List<String> hints;
    private Boolean isPremium;
    private ProblemVisibility visibility;
    private LocalDateTime updatedAt;
}
