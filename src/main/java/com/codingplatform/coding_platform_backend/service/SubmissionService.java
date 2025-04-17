package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.SubmissionDto;
import com.codingplatform.coding_platform_backend.dto.SubmissionRequestDto;

public interface SubmissionService {
    SubmissionRequestDto createSubmission(SubmissionRequestDto submissionRequestDto);
}
