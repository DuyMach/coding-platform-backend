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
                problem.getCreationDate()
        );
    }

    public static Problem mapToProblem(ProblemDto problemDto){
       return new Problem(
               problemDto.getTitle().toUpperCase(),
               problemDto.getDescription(),
               problemDto.getDifficulty()
       );
    }

    public static Set<ProblemDto> mapToProblemDtoSet(List<Problem> problemList){
        return problemList.stream().map(ProblemMapper::mapToProblemDto).collect(Collectors.toSet());
    }
}
