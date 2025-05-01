package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.UpdateProblemDto;
import com.codingplatform.coding_platform_backend.models.Problem;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class UpdateProblemMapper {
    public static UpdateProblemDto mapToDto(Problem problem){
        return new UpdateProblemDto(
                problem.getTitle(),
                problem.getDescription(),
                problem.getDifficulty(),
                problem.getFunctionName(),
                problem.getConstraints(),
                problem.getHint(),
                problem.isPremium(),
                problem.getVisibility(),
                problem.getUpdatedAt()
        );
    }

    public static Problem applyUpdates(Problem problem, UpdateProblemDto updateProblemDto){
        if (StringUtils.hasText(updateProblemDto.getTitle())){
            problem.setTitle(updateProblemDto.getTitle());
        }

        if (StringUtils.hasText(updateProblemDto.getDescription())){
            problem.setDescription(updateProblemDto.getDescription());
        }

        if (updateProblemDto.getDifficulty() != null){
            problem.setDifficulty(updateProblemDto.getDifficulty());
        }

        if (StringUtils.hasText(updateProblemDto.getFunctionName())){
            problem.setFunctionName(updateProblemDto.getFunctionName());
        }

        // Will allow user to overwrite and/or clear existing constraints
        // Will add separate api endpoint to primarily delete or add constraints later on...
        if (updateProblemDto.getConstraints() != null){
            problem.setConstraints(updateProblemDto.getConstraints());
        }

        // Same logic and use case as constraints, will most likely add separate api endpoint to
        // specifically handle adding and removing hints... idk when tho
        if (updateProblemDto.getHints() != null){
            problem.setHint(updateProblemDto.getHints());
        }

        if (updateProblemDto.getIsPremium() != null){
            problem.setPremium(updateProblemDto.getIsPremium());
        }

        if (updateProblemDto.getVisibility() != null){
            problem.setVisibility(updateProblemDto.getVisibility());
        }

        return problem;
    }
}
