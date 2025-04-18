package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.SubmissionDetailsDto;
import com.codingplatform.coding_platform_backend.models.Submission;

public class SubmissionDetailsMapper {
    public static SubmissionDetailsDto mapToSubmissionDetailsDto(Submission submission){
        return new SubmissionDetailsDto(
                submission.getId(),
                submission.getCode(),
                submission.getLanguage(),
                submission.getStatus(),
                submission.getSubmittedAt(),
                submission.getOutput()
        );
    }
}
