package com.codingplatform.coding_platform_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


// For Admin Views
@Data
@AllArgsConstructor
@Builder
public class TestCaseDto {
    private Long id;

    @NotBlank
    private String input;

    @NotBlank
    private String expectedOutput;

    @NotNull
    private Boolean isSample;

    private Long problemId;
}
