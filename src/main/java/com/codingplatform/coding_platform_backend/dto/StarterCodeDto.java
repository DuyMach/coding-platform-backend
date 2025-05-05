package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StarterCodeDto {
    private Long id;

    @NotNull(message = "Language is required")
    private LanguageName language;

    @NotBlank(message = "Starter Code is required")
    private String code;

    private Long problemId;
}
