package com.codingplatform.coding_platform_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Judge0SubmissionResponse {
    private String stdout;

    private String stderr;

    @JsonProperty("compile_output")
    private String compileOutput;

    private Status status;

    @JsonProperty("time")
    private String executionTime;

    private Integer memory;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private int id;
        private String description;
    }
}
