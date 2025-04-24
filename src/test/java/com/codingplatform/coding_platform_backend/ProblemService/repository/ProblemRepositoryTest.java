package com.codingplatform.coding_platform_backend.ProblemService.repository;

import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProblemRepositoryTest {

    private ProblemRepository problemRepository;

    @Autowired
    public ProblemRepositoryTest(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Test
    public void ProblemRepository_Save_ReturnSavedProblem(){
        // Arrange
        Problem problem = Problem.builder()
                .title("Test Problem 1")
                .description("I need job please!")
                .difficulty(Difficulty.HARD)
                .build();

        // Act
        Problem savedProblem = problemRepository.save(problem);

        // Assert
        Assertions.assertThat(savedProblem).isNotNull();
        Assertions.assertThat(savedProblem.getId()).isGreaterThan(0);
        Assertions.assertThat(savedProblem.getTitle()).isEqualTo(problem.getTitle());
    }
}
