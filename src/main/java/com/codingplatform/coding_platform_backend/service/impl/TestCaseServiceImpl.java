package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.dto.mapper.TestCaseMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.TestCase;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.TestCaseRepository;
import com.codingplatform.coding_platform_backend.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    private TestCaseRepository testCaseRepository;
    private ProblemRepository problemRepository;

    public TestCaseServiceImpl(TestCaseRepository testCaseRepository, ProblemRepository problemRepository) {
        this.testCaseRepository = testCaseRepository;
        this.problemRepository = problemRepository;
    }

    @Override
    public TestCaseDto createTestCase(Long problemId, TestCaseDto testCaseDto) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given ID doesn't exist: " + problemId));

        TestCase testCase = TestCaseMapper.mapToEntity(testCaseDto);
        testCase.setProblem(problem);

        TestCase savedTestCase = testCaseRepository.save(testCase);

        return TestCaseMapper.mapToDto(testCase);
    }
}
