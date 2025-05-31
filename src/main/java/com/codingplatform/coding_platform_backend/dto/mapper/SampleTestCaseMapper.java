package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.SampleTestCaseSummaryDto;
import com.codingplatform.coding_platform_backend.models.TestCase;

import java.util.Set;
import java.util.stream.Collectors;

public class SampleTestCaseMapper {
    public static SampleTestCaseSummaryDto mapToDto(TestCase testCase){
        return new SampleTestCaseSummaryDto(
                testCase.getId(),
                testCase.getBeautifiedInputOrDefault(),
                testCase.getExpectedOutput(),
                testCase.isSample(),
                testCase.getExplanation(),
                testCase.getLanguage()
        );
    }

    public static Set<SampleTestCaseSummaryDto> mapToDtoSet(Set<TestCase> testCaseSet){
        return testCaseSet.stream().map(SampleTestCaseMapper::mapToDto).collect(Collectors.toSet());
    }
}
