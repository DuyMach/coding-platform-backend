package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class SampleTestCaseSummaryDto {
    private Long id;
    private String input;
    private String expectedOutput;
    private boolean isSample;
    private String explanation;
    private LanguageName language;
}
