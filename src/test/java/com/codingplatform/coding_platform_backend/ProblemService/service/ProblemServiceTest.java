package com.codingplatform.coding_platform_backend.ProblemService.service;

import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.service.impl.ProblemServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {
    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private ProblemServiceImpl problemService;

    @Test
    public void ProblemService_CreateProblem_ReturnsProblemDto(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .build();

        ProblemDto problemDto = ProblemDto.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .build();

        when(problemRepository.existsByTitle(Mockito.anyString())).thenReturn(false);
        when(problemRepository.save(Mockito.any(Problem.class))).thenReturn(problem);

        // Act
        ProblemDto savedProblemDto = problemService.createProblem(problemDto);

        // Assert
        Assertions.assertThat(savedProblemDto).isNotNull();
    }
}
