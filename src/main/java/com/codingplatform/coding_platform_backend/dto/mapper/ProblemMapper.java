package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.models.Problem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProblemMapper {
    public static ProblemDto mapToProblemDto(Problem problem){
        return new ProblemDto(
                problem.getId(),
                problem.getTitle(),
                problem.getDescription(),
                problem.getDifficulty(),
                problem.getCreationDate(),
                problem.getFunctionName(),
                problem.getConstraints(),
                problem.getHint(),
                problem.isPremium(),
                problem.getUpdatedAt(),
                problem.getVisibility()
        );
    }

    public static Problem mapToProblem(ProblemDto problemDto){
       Problem problem = new Problem();
       problem.setTitle(problemDto.getTitle());
       problem.setDescription(problemDto.getDescription());
       problem.setDifficulty(problemDto.getDifficulty());
       problem.setFunctionName(problemDto.getFunctionName());
       problem.setPremium(problemDto.isPremium());
       problem.setVisibility(problemDto.getVisibility());

       return problem;
    }

    public static Set<ProblemDto> mapToProblemDtoSet(List<Problem> problemList){
        return problemList.stream().map(ProblemMapper::mapToProblemDto).collect(Collectors.toSet());
    }
}
