package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.AddTagsToProblemDto;
import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.dto.TagDto;

import java.util.Set;

public interface ProblemService {
    ProblemDto createProblem(ProblemDto problemDto);
    Set<ProblemDto> getAllProblem();
    Set<TagDto> getAllTagByProblemId(Long problemId);
    String addTagsToProblem(Long problemId, AddTagsToProblemDto addTagsToProblemDto);
}
