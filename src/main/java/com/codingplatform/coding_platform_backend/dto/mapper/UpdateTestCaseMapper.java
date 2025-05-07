package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.UpdateTestCaseDto;
import com.codingplatform.coding_platform_backend.models.TestCase;

public class UpdateTestCaseMapper {
    public static TestCase applyUpdates(TestCase testCase, UpdateTestCaseDto updateTestCaseDto){
        if (updateTestCaseDto.getInput() != null){
            testCase.setInput(updateTestCaseDto.getInput());
        }

        if (updateTestCaseDto.getExpectedOutput() != null){
            testCase.setExpectedOutput(updateTestCaseDto.getExpectedOutput());
        }

        if (updateTestCaseDto.getIsSample() != null) {
            testCase.setSample(updateTestCaseDto.getIsSample());
        }

        return testCase;
    }
}
