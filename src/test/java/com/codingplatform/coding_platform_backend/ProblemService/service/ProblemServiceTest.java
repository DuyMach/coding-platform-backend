package com.codingplatform.coding_platform_backend.ProblemService.service;

import com.codingplatform.coding_platform_backend.dto.AddTagsToProblemDto;
import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.exception.ProblemAlreadyExistException;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.exception.TagNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.ProblemVisibility;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.TagRepository;
import com.codingplatform.coding_platform_backend.service.impl.ProblemServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {
    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private ProblemServiceImpl problemService;

    @Mock
    private TagRepository tagRepository;

    @Test
    public void ProblemService_CreateProblem_ReturnsProblemDto(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        ProblemDto problemDto = ProblemDto.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        when(problemRepository.existsByTitle(problemDto.getTitle())).thenReturn(false);
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
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        when(problemRepository.existsByTitle(problemDto.getTitle())).thenReturn(true);

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
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        Problem problem2 = Problem.builder()
                .title("Combination Sum II")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
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
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem.addTag(array_tag);
        problem.addTag(design_tag);

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));

        // Act
        Set<TagDto> tagDtoSet = problemService.getAllTagByProblemId(problemId);

        // Assert
        Assertions.assertThat(tagDtoSet).isNotNull().isNotEmpty();
        Assertions.assertThat(tagDtoSet.size()).isEqualTo(problem.getTags().size());
        Assertions.assertThat(tagDtoSet)
                .extracting(TagDto::getName)
                .containsExactlyInAnyOrder(TagName.DESIGN, TagName.ARRAYS);
    }

    @Test
    public void ProblemService_GetAllTagByProblemId_ThrowsProblemNotFoundException(){
        // Arrange
        Long problemId = 1L;

        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.getAllTagByProblemId(problemId))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage("Problem with given ID doesn't exist");
    }

    @Test
    public void ProblemService_AddTagsToProblem_ReturnStringSuccess(){
        // Arrange
        Long tagId1 = 1L;
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();

        Long tagId2 = 2L;
        Tag array_tag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();

        Long problemId = 1L;
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        AddTagsToProblemDto addTagsToProblemDto = AddTagsToProblemDto.builder()
                .tagIdSet(Set.of(tagId1, tagId2))
                .build();

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        when(tagRepository.findAllById(Set.of(tagId1, tagId2))).thenReturn(Arrays.asList(design_tag, array_tag));

        // Act
        String message = problemService.addTagsToProblem(problemId, addTagsToProblemDto);

        // What am I even asserting? Generic success string? Why am I talking to myself? Am I going crazy?
        // Need to add feature to display each of the newly TagName because this response is dookie
        Assertions.assertThat(message).isEqualTo("Tags successfully added to problem");
    }

    @Test
    public void ProblemService_AddTagsToProblem_ThrowsProblemNotFoundException(){
        // Arrange
        Long problemId = 1L;

        AddTagsToProblemDto addTagsToProblemDto = AddTagsToProblemDto.builder().build();

        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.addTagsToProblem(problemId, addTagsToProblemDto))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage("Problem with given ID doesn't exist");
    }

    @Test
    public void ProblemService_AddTagsToProblem_ThrowsIllegalArgumentException_WhenTagIdSetIsNull(){
        // Arrange
        Long problemId = 1L;
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        AddTagsToProblemDto addTagsToProblemDto = AddTagsToProblemDto.builder().build();

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.addTagsToProblem(problemId, addTagsToProblemDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Please include at least one tag!");
    }

    @Test
    public void ProblemService_AddTagsToProblem_ThrowsIllegalArgumentException_WhenTagIdSetIsEmpty(){
        // Arrange
        Long problemId = 1L;
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        AddTagsToProblemDto addTagsToProblemDto = AddTagsToProblemDto.builder()
                .tagIdSet(new HashSet<>())
                .build();

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.addTagsToProblem(problemId, addTagsToProblemDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Please include at least one tag!");
    }

    @Test
    public void ProblemService_AddTagsToProblem_ThrowsTagNotFoundException(){
        // Arrange
        Long tagId1 = 1L;
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();

        Long tagId2 = 2L;
        Tag array_tag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();

        Long problemId = 1L;
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        AddTagsToProblemDto addTagsToProblemDto = AddTagsToProblemDto.builder()
                .tagIdSet(Set.of(tagId1, tagId2))
                .build();

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        when(tagRepository.findAllById(Set.of(tagId1, tagId2))).thenReturn(Collections.singletonList(design_tag));

        // Act and Arrange
        Assertions.assertThatThrownBy(() -> problemService.addTagsToProblem(problemId, addTagsToProblemDto))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessage("Tag with given ID doesn't exist!");
    }

    @Test
    public void ProblemService_GetAllProblemByTagId_ReturnsProblemDtoSet(){
        // Arrange
        int expectedSize = 1;

        Long tagId1 = 1L;
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();

        Long problemId = 1L;
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        Problem problem2 = Problem.builder()
                .title("Combination Sum II")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        problem.addTag(design_tag);
        design_tag.setProblems(new HashSet<>(Collections.singletonList(problem)));

        when(tagRepository.findById(tagId1)).thenReturn(Optional.of(design_tag));

        // Act
        Set<ProblemDto> problemDtoSet = problemService.getAllProblemByTagId(tagId1);

        // Assert
        Assertions.assertThat(problemDtoSet).isNotNull().isNotEmpty();
        Assertions.assertThat(problemDtoSet.size()).isEqualTo(expectedSize);
    }

    @Test
    public void ProblemService_GetAllProblemByTagId_ThrowsTagNotFoundException(){
        // Arrange
        Long tagId1 = 1L;

        when (tagRepository.findById(tagId1)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.getAllProblemByTagId(tagId1))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessage("Tag with given ID doesn't exist!");
    }

    @Test
    public void ProblemService_GetProblemById_ReturnsProblemDto(){
        // Assert
        Long problemId = 1L;
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));

        // Act
        ProblemDto problemDto = problemService.getProblemById(problemId);

        // Assert
        Assertions.assertThat(problemDto).isNotNull();
        Assertions.assertThat(problemDto.getTitle()).isEqualTo(problem.getTitle());
    }

    @Test
    public void ProblemService_GetProblemById_ThrowsProblemNotFoundException(){
        // Assert
        Long problemId = 1L;

        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatThrownBy(() -> problemService.getProblemById(problemId))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage("Problem with given ID doesn't exist");
    }

    @Test
    public void ProblemService_GetProblemByDifficulty_ReturnsProblemDtoSet(){
        // Assert
        int expectedSize = 1;
        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        when(problemRepository.findAllByDifficulty(problem.getDifficulty())).thenReturn(Collections.singletonList(problem));

        // Act
        Set<ProblemDto> problemDtoSet = problemService.getAllProblemByDifficulty(problem.getDifficulty());

        // Assert
        Assertions.assertThat(problemDtoSet).isNotNull().isNotEmpty();
        Assertions.assertThat(problemDtoSet.size()).isEqualTo(expectedSize);
    }

    @Test
    public void ProblemService_GetProblemByTagName_ReturnsProblemDtoSet(){
        // Assert
        int expectedSize = 1;

        Long tagId1 = 1L;
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();

        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem.addTag(design_tag);

        when(problemRepository.findByTagName(TagName.DESIGN)).thenReturn(Collections.singletonList(problem));

        // Act
        Set<ProblemDto> problemDtoSet = problemService.getAllProblemsByTagName(TagName.DESIGN);

        // Assert
        Assertions.assertThat(problemDtoSet).isNotNull().isNotEmpty();
        Assertions.assertThat(problemDtoSet.size()).isEqualTo(expectedSize);
    }

    @Test
    public void ProblemService_GetProblemByTagAndDifficulty_ReturnsProblemDtoSet(){
        // Assert
        int expectedSize = 1;

        Long tagId1 = 1L;
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();

        Problem problem = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem.addTag(design_tag);

        when(problemRepository.findByTagNameAndDifficulty(TagName.DESIGN, problem.getDifficulty())).thenReturn(Collections.singletonList(problem));

        // Act
        Set<ProblemDto> problemDtoSet = problemService.getAllProblemsByTagAndDifficulty(TagName.DESIGN, problem.getDifficulty());

        // Assert
        Assertions.assertThat(problemDtoSet).isNotNull();
        Assertions.assertThat(problemDtoSet.size()).isEqualTo(expectedSize);
    }
}
