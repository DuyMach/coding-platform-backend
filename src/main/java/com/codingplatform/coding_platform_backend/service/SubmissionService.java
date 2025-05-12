package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.SubmissionDetailsDto;
import com.codingplatform.coding_platform_backend.dto.SubmissionRequestDto;
import com.codingplatform.coding_platform_backend.dto.SubmissionSummaryDto;

import java.util.Set;

public interface SubmissionService {
    SubmissionDetailsDto createSubmission(SubmissionRequestDto submissionRequestDto);
    Set<SubmissionSummaryDto> getAllSubmissionByUserIdAndProblemId(Long userId, Long problemId);
    SubmissionDetailsDto getSubmissionById(Long submissionId);
}
