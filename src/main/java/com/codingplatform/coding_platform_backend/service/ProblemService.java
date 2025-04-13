package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.ProblemDto;

import java.util.Set;

public interface ProblemService {
    ProblemDto createProblem(ProblemDto problemDto);
    Set<ProblemDto> getAllProblem();

}
