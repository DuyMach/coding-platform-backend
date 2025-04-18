package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.SubmissionRequestDto;
import com.codingplatform.coding_platform_backend.models.Submission;
import lombok.Data;

@Data
public class SubmissionRequestMapper {
    public static SubmissionRequestDto mapToSubmissionDto(Submission submission){
        return new SubmissionRequestDto(
                submission.getCode(),
                submission.getLanguage(),
                submission.getUser().getId(),
                submission.getProblem().getId()
        );
    }

    public static Submission mapToSubmission(SubmissionRequestDto submissionRequestDto){
        Submission submission = new Submission();
        submission.setCode(submissionRequestDto.getCode());
        submission.setLanguage(submissionRequestDto.getLanguage());
        return submission;
    }
}
