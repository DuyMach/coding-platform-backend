package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.models.TestCase;

import java.util.Set;
import java.util.stream.Collectors;

public class TestCaseMapper {
    public static TestCaseDto mapToDto(TestCase testCase){
        return new TestCaseDto(
                testCase.getId(),
                testCase.getInput(),
                testCase.getExpectedOutput(),
                testCase.isSample(),
                testCase.getProblem().getId(),
                testCase.getLanguage(),
                testCase.getExplanation(),
                testCase.getBeautifiedInput()
        );
    }

    public static TestCase mapToEntity(TestCaseDto testCaseDto){
        TestCase testCase = new TestCase();

        testCase.setInput(testCaseDto.getInput());
        testCase.setExpectedOutput(testCaseDto.getExpectedOutput());
        testCase.setLanguage(testCaseDto.getLanguage());
        testCase.setExplanation(testCaseDto.getExplanation());
        testCase.setBeautifiedInput(testCaseDto.getBeautifiedInput());


        if (testCaseDto.getIsSample() != null){
            testCase.setSample(testCaseDto.getIsSample());
        } else {
            testCase.setSample(false);
        }

        return testCase;
    }

    public static Set<TestCaseDto> mapToDtoSet(Set<TestCase> testCases){
        return testCases.stream().map(TestCaseMapper::mapToDto).collect(Collectors.toSet());
    }
}
