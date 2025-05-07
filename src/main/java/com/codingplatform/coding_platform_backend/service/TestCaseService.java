package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.dto.UpdateTestCaseDto;

import java.util.Set;

public interface TestCaseService {
    TestCaseDto createTestCase(Long problemId, TestCaseDto testCaseDto);
    Set<TestCaseDto> getAllTestCasesByProblemId(Long problemId);
    TestCaseDto updateTestCaseById(Long testCaseId, UpdateTestCaseDto updateTestCaseDto);
}
