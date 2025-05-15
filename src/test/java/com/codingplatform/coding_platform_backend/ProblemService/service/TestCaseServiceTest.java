package com.codingplatform.coding_platform_backend.ProblemService.service;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.dto.mapper.TestCaseMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.TestCase;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.ProblemVisibility;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.TestCaseRepository;
import com.codingplatform.coding_platform_backend.service.impl.TestCaseServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCaseServiceTest {
    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private TestCaseServiceImpl testCaseService;

    Problem problem;

    TestCase testCase;

    TestCaseDto testCaseDto;

    Long problemId = 1L;

    @BeforeEach
    void beforeEach() {
        problem = Problem.builder()
                .title("Print Input String.")
                .description("Given a String s, output the String to console")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("printString")
                .isPremium(false)
                .id(problemId)
                .build();

        testCase = TestCase.builder()
                .input("String s = 'hello world'")
                .expectedOutput("hello world")
                .isSample(true)
                .problem(problem)
                .build();

        testCaseDto = TestCaseDto.builder()
                .input("String s = 'hello world'")
                .expectedOutput("hello world")
                .isSample(true)
                .problemId(problem.getId())
                .build();
    }

    @Test
    public void TestCaseService_CreateTestCase_ReturnsTestCaseDto(){
        // Arrange
        when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
        when(testCaseRepository.save(Mockito.any(TestCase.class))).thenReturn(testCase);

        // Act
        TestCaseDto result = testCaseService.createTestCase(problemId, testCaseDto);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getExpectedOutput()).isEqualTo(testCase.getExpectedOutput());
    }

    @Test
    public void TestCaseService_CreateTestCase_ThrowsProblemNotFoundException_WhenProblemNotFound(){
        // Arrange
        when(problemRepository.findById(problemId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatThrownBy(() -> testCaseService.createTestCase(problemId, testCaseDto))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage("Problem with given ID doesn't exist: " + problemId);
    }

    @Test
    public void TestCaseService_GetAllTestCasesByProblemId_ReturnsTestCaseDtoSet(){
        // Arrange
        int expectedSize = 3;

        TestCase testCase1 = TestCase.builder()
                .input("String s = ''")
                .expectedOutput("")
                .isSample(false)
                .problem(problem)
                .build();

        TestCase testCase2 = TestCase.builder()
                .input("String s = 'world hello'")
                .expectedOutput("world hello")
                .isSample(false)
                .problem(problem)
                .build();

        when(problemRepository.existsById(problemId)).thenReturn(true);
        when(testCaseRepository.findAllByProblemId(problemId)).thenReturn(Set.of(testCase, testCase1, testCase2));

        // Act
        Set<TestCaseDto> testCaseDtoSet = testCaseService.getAllTestCasesByProblemId(problemId);

        // Assert
        Assertions.assertThat(testCaseDtoSet).isNotNull().hasSize(expectedSize);
        Assertions.assertThat(testCaseDtoSet).containsExactlyInAnyOrderElementsOf(TestCaseMapper.mapToDtoSet(Set.of(testCase1, testCase2, testCase)));
    }

    @Test
    public void TestCaseService_GetAllTestCasesByProblemId_ThrowsProblemNotFoundException_WhenProblemNotFound(){
        // Arrange
        when(problemRepository.existsById(problemId)).thenReturn(false);

        // Act and Assert
        Assertions.assertThatThrownBy(() -> testCaseService.getAllTestCasesByProblemId(problemId))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage("Problem with given ID doesn't exist: " + problemId);
    }
}
