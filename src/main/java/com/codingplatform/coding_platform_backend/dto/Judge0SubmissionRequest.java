package com.codingplatform.coding_platform_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Judge0SubmissionRequest {
    @JsonProperty("language_id")
    private int languageId;

    @JsonProperty("source_code")
    private String sourceCode;

    private String stdin;

    @JsonProperty("expected_output")
    private String expectedOutput;

    @JsonProperty("redirect_stderr_to_stdout")
    private boolean redirectStderrToStdout = true;
}
