package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.SubmissionSummaryDto;
import com.codingplatform.coding_platform_backend.models.Submission;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubmissionSummaryMapper {
    public static SubmissionSummaryDto mapToSubmissionSummaryDto(Submission submission){
        return new SubmissionSummaryDto(
                submission.getId(),
                submission.getLanguage(),
                submission.getStatus(),
                submission.getSubmittedAt()
        );
    }

    public static Set<SubmissionSummaryDto> mapToSubmissionSummaryDtoSet(List<Submission> submissionList){
        return submissionList.stream().map(SubmissionSummaryMapper::mapToSubmissionSummaryDto).collect(Collectors.toSet());
    }
}
