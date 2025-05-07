package com.codingplatform.coding_platform_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTestCaseDto {
    private String input;
    private String expectedOutput;
    private Boolean isSample;
}
