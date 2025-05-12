package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WrapperTemplateDto {
    private Long id;

    @NotNull
    private LanguageName language;

    @NotBlank
    private String code;

    private Long problemId;
}
