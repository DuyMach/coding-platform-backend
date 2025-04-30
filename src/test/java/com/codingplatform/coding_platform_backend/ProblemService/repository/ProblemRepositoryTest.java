package com.codingplatform.coding_platform_backend.ProblemService.repository;

import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.ProblemVisibility;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProblemRepositoryTest {

    private ProblemRepository problemRepository;
    private TagRepository tagRepository;

    @Autowired
    public ProblemRepositoryTest(ProblemRepository problemRepository, TagRepository tagRepository) {
        this.problemRepository = problemRepository;
        this.tagRepository = tagRepository;
    }

    @Test
    public void ProblemRepository_Save_ReturnsSavedProblem(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Test Problem 1")
                .description("I need job please!")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();

        // Act
        Problem savedProblem = problemRepository.save(problem);

        // Assert
        Assertions.assertThat(savedProblem).isNotNull();
        Assertions.assertThat(savedProblem.getId()).isGreaterThan(0);
        Assertions.assertThat(savedProblem.getTitle()).isEqualTo(problem.getTitle());
    }

    @Test
    public void ProblemRepository_FindAll_ReturnsProblemList(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Test Problem")
                .description("I need job please!")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problemRepository.save(problem);

        Problem problem1 = Problem.builder()
                .title("I am Cold")
                .description("I live in New York, The Cold Doesn't Bother Me Anyways")
                .difficulty(Difficulty.EASY)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problemRepository.save(problem1);

        // Act
        List<Problem> problems = problemRepository.findAll();

        // Assert DOMINANCE!
        Assertions.assertThat(problems).isNotNull();
        Assertions.assertThat(problems.size()).isEqualTo(2);
    }

    @Test
    public void ProblemRepository_FindById_ReturnsProblem(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Test Problem")
                .description("I need job please!")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problemRepository.save(problem);

        // Act
        Optional<Problem> result = problemRepository.findById(problem.getId());

        // Assert
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getTitle()).isEqualTo(problem.getTitle());
    }

    @Test
    public void ProblemRepository_FindAllByDifficulty_ReturnsProblemList(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Test Problem")
                .description("I need job please!")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problemRepository.save(problem);

        Problem problem1 = Problem.builder()
                .title("I am Cold")
                .description("I live in New York, The Cold Doesn't Bother Me Anyways")
                .difficulty(Difficulty.MEDIUM)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problemRepository.save(problem1);

        Problem problem2 = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.MEDIUM)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problemRepository.save(problem2);

        // Act
        List<Problem> easyList = problemRepository.findAllByDifficulty(Difficulty.EASY);
        List<Problem> mediumList = problemRepository.findAllByDifficulty(Difficulty.MEDIUM);
        List<Problem> hardList = problemRepository.findAllByDifficulty(Difficulty.HARD);

        // Assert
        Assertions.assertThat(easyList).isNotNull().isEmpty();

        Assertions.assertThat(mediumList).isNotNull();
        Assertions.assertThat(mediumList.size()).isEqualTo(2);

        Assertions.assertThat(hardList).isNotNull();
        Assertions.assertThat(hardList.size()).isEqualTo(1);
    }

    @Test
    public void ProblemRepository_FindAllByTagName_ReturnsProblemList(){
        // Arrange
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();
        tagRepository.save(design_tag);

        Tag array_tag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();
        tagRepository.save(array_tag);

        Problem problem = Problem.builder()
                .title("Test Problem")
                .description("I need job please!")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem.addTag(design_tag);
        problem.addTag(array_tag);
        problemRepository.save(problem);

        Problem problem1 = Problem.builder()
                .title("I am Cold")
                .description("I live in New York, The Cold Doesn't Bother Me Anyways")
                .difficulty(Difficulty.MEDIUM)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem1.addTag(design_tag);
        problemRepository.save(problem1);

        // Act
        List<Problem> problems_byTag_arrays = problemRepository.findByTagName(TagName.ARRAYS);
        List<Problem> problems_byTag_design = problemRepository.findByTagName(TagName.DESIGN);
        List<Problem> problems_byTag_linkedList = problemRepository.findByTagName(TagName.LINKED_LIST);

        // Assert
        Assertions.assertThat(problems_byTag_arrays).isNotNull().isNotEmpty();
        Assertions.assertThat(problems_byTag_design).isNotNull().isNotEmpty();
        Assertions.assertThat(problems_byTag_linkedList).isNotNull().isEmpty();

        Assertions.assertThat(problems_byTag_arrays.size()).isEqualTo(1);
        Assertions.assertThat(problems_byTag_design.size()).isEqualTo(2);
    }

    @Test
    public void ProblemRepository_FindAllByTagNameAndDifficulty_ReturnsProblemList(){
        // Arrange
        Tag design_tag = Tag.builder()
                .name(TagName.DESIGN)
                .build();
        tagRepository.save(design_tag);

        Tag array_tag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();
        tagRepository.save(array_tag);

        Problem problem = Problem.builder()
                .title("Test Problem")
                .description("I need job please!")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem.addTag(design_tag);
        problem.addTag(array_tag);
        problemRepository.save(problem);

        Problem problem1 = Problem.builder()
                .title("I am Cold")
                .description("I live in New York, The Cold Doesn't Bother Me Anyways")
                .difficulty(Difficulty.MEDIUM)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem1.addTag(design_tag);
        problemRepository.save(problem1);

        Problem problem2 = Problem.builder()
                .title("Combination Sum")
                .description("Given an array of distinct integers candidates and a target integer target," +
                        " return a list of all unique combinations of candidates where the chosen numbers sum to target." +
                        " You may return the combinations in any order.")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("testFunction")
                .isPremium(false)
                .build();
        problem2.addTag(array_tag);
        problemRepository.save(problem2);

        // Act
        List<Problem> problems_byTag_arrays_difficulty_HARD = problemRepository.findByTagNameAndDifficulty(TagName.ARRAYS, Difficulty.HARD);
        List<Problem> problems_byTag_design_difficulty_MEDIUM = problemRepository.findByTagNameAndDifficulty(TagName.DESIGN, Difficulty.MEDIUM);
        List<Problem> problems_byTag_linkedList_difficulty_EASY = problemRepository.findByTagNameAndDifficulty(TagName.LINKED_LIST, Difficulty.EASY);

        // Assert
        Assertions.assertThat(problems_byTag_arrays_difficulty_HARD).isNotNull().isNotEmpty();
        Assertions.assertThat(problems_byTag_design_difficulty_MEDIUM).isNotNull().isNotEmpty();
        Assertions.assertThat(problems_byTag_linkedList_difficulty_EASY).isNotNull().isEmpty();

        Assertions.assertThat(problems_byTag_arrays_difficulty_HARD.size()).isEqualTo(2);
        Assertions.assertThat(problems_byTag_design_difficulty_MEDIUM.size()).isEqualTo(1);
    }
}
