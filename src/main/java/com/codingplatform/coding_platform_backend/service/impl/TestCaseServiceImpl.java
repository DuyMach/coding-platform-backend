package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.dto.UpdateTestCaseDto;
import com.codingplatform.coding_platform_backend.dto.mapper.TestCaseMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.UpdateTestCaseMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.exception.TestCaseNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.TestCase;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.TestCaseRepository;
import com.codingplatform.coding_platform_backend.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    private TestCaseRepository testCaseRepository;
    private ProblemRepository problemRepository;

    @Autowired
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

    @Override
    public Set<TestCaseDto> getAllTestCasesByProblemId(Long problemId) {
        if (!problemRepository.existsById(problemId)){
            throw new ProblemNotFoundException("Problem with given ID doesn't exist: " + problemId);
        }

        Set<TestCase> testCases = testCaseRepository.findAllByProblemId(problemId);

        return TestCaseMapper.mapToDtoSet(testCases);
    }

    @Override
    public TestCaseDto updateTestCaseById(Long testCaseId, UpdateTestCaseDto updateTestCaseDto) {
        // Will implement custom exception and global handling later
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new TestCaseNotFoundException("Test Case with given ID doesn't exist: " + testCaseId));

        // Prevents user from accidentally clearing the input
        if (updateTestCaseDto.getInput() != null && updateTestCaseDto.getInput().trim().isEmpty()){
            throw new IllegalArgumentException("Cannot set the input field to nothing! " +
                    "If you do not wish to update the input field, " +
                    "please remove it from the JSON request. Only include what you want to update.");
        }

        // Prevents user from accidentally clearing the expectedOutput
        if (updateTestCaseDto.getExpectedOutput() != null && updateTestCaseDto.getExpectedOutput().trim().isEmpty()){
            throw new IllegalArgumentException("Cannot set the expectedOutput field to nothing! " +
                    "If you do not wish to update the expectedOutput field, " +
                    "please remove it from the JSON request. Only include what you want to update.");
        }

        TestCase updatedTestCase = UpdateTestCaseMapper.applyUpdates(testCase, updateTestCaseDto);
        TestCase savedTestCase = testCaseRepository.save(updatedTestCase);

        return TestCaseMapper.mapToDto(savedTestCase);
    }

    @Override
    public String deleteTestCase(Long testCaseId) {
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new TestCaseNotFoundException("Test Case with given ID doesn't exist: " + testCaseId));

        testCaseRepository.delete(testCase);
        return "Test Case deleted successfully (ID): " + testCase;
    }

    @Override
    public Set<TestCaseDto> getAllSampleTestCasesByProblemId(Long problemId) {
        if (!problemRepository.existsById(problemId)){
            throw new ProblemNotFoundException("Problem with given ID doesn't exist: " + problemId);
        }

        Set<TestCase> testCases = testCaseRepository.findAllByProblemIdAndIsSampleTrue(problemId);
        return TestCaseMapper.mapToDtoSet(testCases);
    }
}
