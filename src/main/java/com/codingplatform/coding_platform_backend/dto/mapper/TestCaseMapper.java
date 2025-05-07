package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.models.TestCase;

public class TestCaseMapper {
    public static TestCaseDto mapToDto(TestCase testCase){
        return new TestCaseDto(
                testCase.getId(),
                testCase.getInput(),
                testCase.getExpectedOutput(),
                testCase.isSample(),
                testCase.getProblem().getId()
        );
    }

    public static TestCase mapToEntity(TestCaseDto testCaseDto){
        TestCase testCase = new TestCase();

        testCase.setInput(testCaseDto.getInput());
        testCase.setExpectedOutput(testCaseDto.getExpectedOutput());

        if (testCaseDto.getIsSample() != null){
            testCase.setSample(testCaseDto.getIsSample());
        } else {
            testCase.setSample(false);
        }

        return testCase;
    }
}
