package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import com.codingplatform.coding_platform_backend.models.enums.StatusName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionSummaryDto {
    private Long id;
    private LanguageName language;
    private StatusName status;
    private LocalDateTime submittedAt;
}
