package com.codingplatform.coding_platform_backend.ProblemService.service;

import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.exception.ProblemAlreadyExistException;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.service.impl.ProblemServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Test
    public void ProblemService_CreateProblem_ThrowsProblemAlreadyExistException(){
        // Arrange
        ProblemDto problemDto = ProblemDto.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .build();
        when(problemRepository.existsByTitle(Mockito.anyString())).thenReturn(true);

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.createProblem(problemDto))
                .isInstanceOf(ProblemAlreadyExistException.class)
                .hasMessage("This title is already taken!");
    }

    @Test
    public void ProblemService_GetAllProblem_ReturnsProblemDtoSet(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .build();
        Problem problem2 = Problem.builder()
                .title("Combination Sum II")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .build();

        List<Problem> problemDtoList = Arrays.asList(problem, problem2);
        when(problemRepository.findAll()).thenReturn(problemDtoList);

        // Act
        Set<ProblemDto> problemDtoSet = problemService.getAllProblem();

        // Assert
        Assertions.assertThat(problemDtoSet).isNotNull().isNotEmpty();
        Assertions.assertThat(problemDtoSet.size()).isEqualTo(problemDtoList.size());
    }

    @Test
    public void ProblemService_GetAllTagByProblemId_ReturnsTagDtoSet(){
        // Arrange
        Long problemId = 1L;
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();

        Tag array_tag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();

        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .build();
        problem.addTag(array_tag);
        problem.addTag(design_tag);

        when(problemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(problem));

        // Act
        Set<TagDto> tagDtoSet = problemService.getAllTagByProblemId(problemId);

        // Assert
        Assertions.assertThat(tagDtoSet).isNotNull().isNotEmpty();
        Assertions.assertThat(tagDtoSet.size()).isEqualTo(problem.getTags().size());
        Assertions.assertThat(tagDtoSet)
                .extracting(tagDto -> tagDto.getName())
                .containsExactlyInAnyOrder(TagName.DESIGN, TagName.ARRAYS);
    }

    @Test
    public void ProblemService_GetAllTagByProblemId_ThrowsProblemNotFoundException(){
        // Arrange
        Long problemId = 1L;

        when(problemRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.getAllTagByProblemId(problemId))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage("Problem with given ID doesn't exist");
    }
}
