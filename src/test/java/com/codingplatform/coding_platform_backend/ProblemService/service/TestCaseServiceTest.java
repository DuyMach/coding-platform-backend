package com.codingplatform.coding_platform_backend.ProblemService.service;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.dto.UpdateTestCaseDto;
import com.codingplatform.coding_platform_backend.dto.mapper.TestCaseMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.exception.TestCaseNotFoundException;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCaseServiceTest {
    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private TestCaseServiceImpl testCaseService;

    private Problem problem;

    private TestCase testCase;

    private TestCaseDto testCaseDto;

    private final Long problemId = 1L;


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
        String PROBLEM_NOT_FOUND_ERROR = "Problem with given ID doesn't exist: ";
        Assertions.assertThatThrownBy(() -> testCaseService.createTestCase(problemId, testCaseDto))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage(PROBLEM_NOT_FOUND_ERROR + problemId);
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
    public void TestCaseService_GetAllTestCasesByProblemId_ReturnsEmptySet_WhenNoTestCaseExist(){
        // Arrange
        Long problemId = 1L;

        when(problemRepository.existsById(problemId)).thenReturn(true);
        when(testCaseRepository.findAllByProblemId(problemId)).thenReturn(Set.of());

        // Act
        Set<TestCaseDto> result = testCaseService.getAllTestCasesByProblemId(problemId);

        // Assert
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void TestCaseService_GetAllTestCasesByProblemId_ThrowsProblemNotFoundException_WhenProblemNotFound(){
        // Arrange
        when(problemRepository.existsById(problemId)).thenReturn(false);

        // Act and Assert
        String PROBLEM_NOT_FOUND_ERROR = "Problem with given ID doesn't exist: ";
        Assertions.assertThatThrownBy(() -> testCaseService.getAllTestCasesByProblemId(problemId))
                .isInstanceOf(ProblemNotFoundException.class)
                .hasMessage(PROBLEM_NOT_FOUND_ERROR + problemId);
    }

    @Test
    public void TestCaseService_UpdateTestCaseById_ReturnsUpdatedTestCaseDto(){
        // Arrange
        Long testCaseId = 1L;
        UpdateTestCaseDto updateTestCaseDto = UpdateTestCaseDto.builder()
                .input("updated test case input")
                .expectedOutput("nothing")
                .isSample(false)
                .build();

        TestCase updatedTestCase = TestCase.builder()
                .input("updated test case input")
                .expectedOutput("nothing")
                .isSample(false)
                .problem(problem)
                .build();

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.of(testCase));
        when(testCaseRepository.save(Mockito.any(TestCase.class))).thenReturn(updatedTestCase);

        // Act
        TestCaseDto result = testCaseService.updateTestCaseById(testCaseId, updateTestCaseDto);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getExpectedOutput()).isEqualTo(updateTestCaseDto.getExpectedOutput());
        Assertions.assertThat(result.getIsSample()).isFalse();
    }

    @Test
    public void TestCaseService_UpdateTestCaseById_SavesCorrectUpdatedEntity(){
        // Arrange
        Long testCaseId = 1L;
        UpdateTestCaseDto updateTestCaseDto = UpdateTestCaseDto.builder()
                .input("updated test case input")
                .expectedOutput("nothing")
                .isSample(false)
                .build();

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.of(testCase));
        when(testCaseRepository.save(Mockito.any(TestCase.class))).thenReturn(testCase); // return something to avoid null

        // Act
        TestCaseDto result = testCaseService.updateTestCaseById(testCaseId, updateTestCaseDto);

        // Assert
        ArgumentCaptor<TestCase> captor = ArgumentCaptor.forClass(TestCase.class);
        verify(testCaseRepository).save(captor.capture());

        TestCase captured = captor.getValue();
        Assertions.assertThat(captured.getExpectedOutput()).isEqualTo(updateTestCaseDto.getExpectedOutput());
        Assertions.assertThat(captured.getInput()).isEqualTo(updateTestCaseDto.getInput());
        Assertions.assertThat(captured.isSample()).isFalse();
        Assertions.assertThat(captured.getProblem()).isEqualTo(testCase.getProblem());
    }

    @Test
    public void TestCaseService_UpdateTestCaseById_ThrowsTestCaseNotFoundException_WhenNotFound(){
        // Arrange
        Long testCaseId = 1L;
        UpdateTestCaseDto updateTestCaseDto = UpdateTestCaseDto.builder()
                .input("updated test case input")
                .expectedOutput("nothing")
                .isSample(false)
                .build();

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.empty());

        // Act and Assert
        String TESTCASE_NOT_FOUND_ERROR = "Test Case with given ID doesn't exist: ";
        Assertions.assertThatThrownBy(() -> testCaseService.updateTestCaseById(testCaseId, updateTestCaseDto))
                .isInstanceOf(TestCaseNotFoundException.class)
                .hasMessage(TESTCASE_NOT_FOUND_ERROR + testCaseId);
    }

    @Test
    public void TestCaseService_UpdateTestCaseById_ThrowsIllegalArgumentException_WhenInputIsBlank(){
        // Arrange
        Long testCaseId = 1L;
        UpdateTestCaseDto updateTestCaseDto = UpdateTestCaseDto.builder()
                .input(" ") // blank input field
                .expectedOutput("nothing")
                .isSample(false)
                .build();

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.of(testCase));

        // Act and Assert
        String EMPTY_INPUT_ERROR = "Cannot set the input field to nothing! " +
                "If you do not wish to update the input field, " +
                "please remove it from the JSON request. Only include what you want to update.";
        Assertions.assertThatThrownBy(() -> testCaseService.updateTestCaseById(testCaseId, updateTestCaseDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_INPUT_ERROR);
    }

    @Test
    public void TestCaseService_UpdateTestCaseById_ThrowsIllegalArgumentException_WhenExpectedOutputIsBlank(){
        // Arrange
        Long testCaseId = 1L;
        UpdateTestCaseDto updateTestCaseDto = UpdateTestCaseDto.builder()
                .input("updated input")
                .expectedOutput(" ") // blank expectedOutput field
                .isSample(false)
                .build();

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.of(testCase));

        // Act and Assert
        String EMPTY_EXPECTED_OUTPUT_ERROR = "Cannot set the expectedOutput field to nothing! " +
                "If you do not wish to update the expectedOutput field, " +
                "please remove it from the JSON request. Only include what you want to update.";
        Assertions.assertThatThrownBy(() -> testCaseService.updateTestCaseById(testCaseId, updateTestCaseDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_EXPECTED_OUTPUT_ERROR);
    }

    @Test
    public void TestCaseService_UpdateTestCaseById_ReturnsUpdatedTestCaseDto_WhenInputAndExpectedOutputIsMissing(){
        // Arrange
        Long testCaseId = 1L;
        UpdateTestCaseDto updateTestCaseDto = UpdateTestCaseDto.builder()
//                .input("updated test case input") commented out input inside the updateDto
//                .expectedOutput("nothing") commented out expectedOutput inside updateDto
                .isSample(false)
                .build();

        TestCase updatedTestCase = TestCase.builder()
                .input("String s = 'hello world'")
                .expectedOutput("hello world")
                .isSample(false)
                .problem(problem)
                .build();

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.of(testCase));
        when(testCaseRepository.save(Mockito.any(TestCase.class))).thenReturn(updatedTestCase);

        // Act
        TestCaseDto result = testCaseService.updateTestCaseById(testCaseId, updateTestCaseDto);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getInput()).isEqualTo(updatedTestCase.getInput());
        Assertions.assertThat(result.getExpectedOutput()).isEqualTo(updatedTestCase.getExpectedOutput());
        Assertions.assertThat(result.getIsSample()).isFalse();
    }

    @Test
    public void TestCaseService_DeleteTestCase_ReturnsSuccessString(){
        // Arrange
        Long testCaseId = 1L;

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.of(testCase));

        // Act
        String message = testCaseService.deleteTestCase(testCaseId);

        // Assert
        Assertions.assertThat(message).isNotBlank().isEqualTo("Test Case deleted successfully (ID): " + testCase);
    }

    @Test
    public void TestCaseService_DeleteTestCase_ThrowsTestCaseNotFoundException_WhenNotFound(){
        // Arrange
        Long testCaseId = 1L;

        when(testCaseRepository.findById(testCaseId)).thenReturn(Optional.empty());

        // Act and Assert
        String TESTCASE_NOT_FOUND_ERROR = "Test Case with given ID doesn't exist: ";
        Assertions.assertThatThrownBy(() -> testCaseService.deleteTestCase(testCaseId))
                .isInstanceOf(TestCaseNotFoundException.class)
                .hasMessage(TESTCASE_NOT_FOUND_ERROR + testCaseId);
    }
}
