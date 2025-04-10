package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import com.codingplatform.coding_platform_backend.models.enums.StatusName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionDto {
    private Long id;
    private String code;
    private LanguageName language;
    private StatusName status;
    private LocalDateTime submittedAt;
    private String output;
    private Long userId;
    private Long problemId;
}
