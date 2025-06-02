package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.*;
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
    ProblemResponse getAllProblemPageable(int pageNo, int pageSize);
    ProblemResponse getAllProblemByDifficultyPageable(Difficulty difficulty, int pageNo, int pageSize);
    ProblemResponse getAllProblemsByTagNamePageable(TagName tagName, int pageNo, int pageSize);
    ProblemResponse getAllProblemsByTagAndDifficultyPageable(TagName tagName, Difficulty difficulty, int pageNo, int pageSize);
}
