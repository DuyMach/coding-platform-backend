package com.codingplatform.coding_platform_backend.dto;

import lombok.Data;


// For Admin Views
@Data
public class TestCaseDto {
    private Long id;
    private String input;
    private String expectedOutput;
    private boolean isSample;
}
