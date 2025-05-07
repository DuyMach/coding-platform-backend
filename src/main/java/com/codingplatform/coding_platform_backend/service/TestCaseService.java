package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;

public interface TestCaseService {
    TestCaseDto createTestCase(Long problemId, TestCaseDto testCaseDto);
}
