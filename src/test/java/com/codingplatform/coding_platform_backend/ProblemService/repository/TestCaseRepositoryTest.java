package com.codingplatform.coding_platform_backend.ProblemService.repository;

import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.TestCase;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.ProblemVisibility;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.TestCaseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TestCaseRepositoryTest {
    private TestCaseRepository testCaseRepository;
    private ProblemRepository problemRepository;

    Problem savedProblem;

    @Autowired
    public TestCaseRepositoryTest(TestCaseRepository testCaseRepository, ProblemRepository problemRepository) {
        this.testCaseRepository = testCaseRepository;
        this.problemRepository = problemRepository;
    }

    @BeforeEach
    void beforeEach() {
        Problem problem = Problem.builder()
                .title("Print Input String.")
                .description("Given a String s, output the String to console")
                .difficulty(Difficulty.HARD)
                .visibility(ProblemVisibility.PUBLIC)
                .functionName("printString")
                .isPremium(false)
                .build();

        savedProblem = problemRepository.save(problem);
    }

    @Test
    public void TestCaseRepository_Save_ReturnsSavedTestCase(){
        // Arrange
        TestCase testCase = TestCase.builder()
                .input("String s = 'hello world'")
                .expectedOutput("hello world")
                .isSample(true)
                .problem(savedProblem)
                .build();

        // Act
        TestCase savedTestCase = testCaseRepository.save(testCase);

        // Assert
        Assertions.assertThat(savedTestCase).isNotNull();
        Assertions.assertThat(savedTestCase.getProblem().getId()).isEqualTo(savedProblem.getId());
        Assertions.assertThat(savedTestCase.getInput()).isEqualTo(testCase.getInput());
    }

    @Test
    public void TestCaseRepository_FindAllByProblemId_ReturnsTestCaseSet(){
        // Arrange
        TestCase testCase = TestCase.builder()
                .input("String s = 'hello world'")
                .expectedOutput("hello world")
                .isSample(true)
                .problem(savedProblem)
                .build();
        testCaseRepository.save(testCase);

        TestCase testCase2 = TestCase.builder()
                .input("String s = ''")
                .expectedOutput("")
                .isSample(false)
                .problem(savedProblem)
                .build();
        testCaseRepository.save(testCase2);

        TestCase testCase3 = TestCase.builder()
                .input("String s = 'world hello'")
                .expectedOutput("world hello")
                .isSample(false)
                .problem(savedProblem)
                .build();
        testCaseRepository.save(testCase3);

        // Act
        Set<TestCase> testCases = testCaseRepository.findAllByProblemId(savedProblem.getId());

        // Assert
        Assertions.assertThat(testCases).isNotNull().containsExactlyInAnyOrder(testCase2, testCase, testCase3);
    }

    @Test
    public void TestCaseRepository_FindById_ReturnsTestCase(){
        // Arrange
        TestCase testCase = TestCase.builder()
                .input("String s = 'hello world'")
                .expectedOutput("hello world")
                .isSample(true)
                .problem(savedProblem)
                .build();
        testCaseRepository.save(testCase);

        // Act
        Optional<TestCase> result = testCaseRepository.findById(testCase.getId());

        // Assert
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getExpectedOutput()).isEqualTo(testCase.getExpectedOutput());
    }

    @Test
    public void TestCaseRepository_FindById_ReturnsEmptyOptional_WhenNotFound(){
        // Act
        Optional<TestCase> result = testCaseRepository.findById(1L);

        // Assert
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void TestCaseRepository_Delete_ReturnsEmptyOptional(){
        // Arrange
        TestCase testCase = TestCase.builder()
                .input("String s = 'hello world'")
                .expectedOutput("hello world")
                .isSample(true)
                .problem(savedProblem)
                .build();
        testCaseRepository.save(testCase);

        // Act
        testCaseRepository.delete(testCase);
        Optional<TestCase> result = testCaseRepository.findById(testCase.getId());

        // Assert
        Assertions.assertThat(result).isNotPresent();
    }
}
