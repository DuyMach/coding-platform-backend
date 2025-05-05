package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.AddTagsToProblemDto;
import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.dto.UpdateProblemDto;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.TagName;

import java.util.Set;

public interface ProblemService {
    ProblemDto createProblem(ProblemDto problemDto);
    Set<ProblemDto> getAllProblem();
    Set<TagDto> getAllTagByProblemId(Long problemId);
    String addTagsToProblem(Long problemId, AddTagsToProblemDto addTagsToProblemDto);
    Set<ProblemDto> getAllProblemByTagId(Long tagId);
    ProblemDto getProblemById(Long problemId);
    Set<ProblemDto> getAllProblemByDifficulty(Difficulty difficulty);
    Set<ProblemDto> getAllProblemsByTagName(TagName tagName);
    Set<ProblemDto> getAllProblemsByTagAndDifficulty(TagName tagName, Difficulty difficulty);
    UpdateProblemDto updateProblem(Long problemId, UpdateProblemDto updateProblemDto);
}
