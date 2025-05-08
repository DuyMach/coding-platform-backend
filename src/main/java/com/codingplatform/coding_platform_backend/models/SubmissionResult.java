package com.codingplatform.coding_platform_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean passed;

    @Column(columnDefinition = "TEXT")
    private String actualOutput;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    private Long executionTimeMs;

    private Long memoryUsedKb;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;
}
