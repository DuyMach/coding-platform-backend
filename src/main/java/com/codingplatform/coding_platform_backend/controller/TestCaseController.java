package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.dto.UpdateTestCaseDto;
import com.codingplatform.coding_platform_backend.service.TestCaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class TestCaseController {
    private TestCaseService testCaseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping("/problems/{id}/test-case")
    public ResponseEntity<TestCaseDto> createTestCase(
            @PathVariable ("id") Long problemId,
            @Valid @RequestBody TestCaseDto testCaseDto)
    {
        TestCaseDto result = testCaseService.createTestCase(problemId, testCaseDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/problems/{id}/test-cases")
    public ResponseEntity<Set<TestCaseDto>> getAllTestCasesByProblemId(@PathVariable("id") Long problemId){
        Set<TestCaseDto> testCaseDtoSet = testCaseService.getAllTestCasesByProblemId(problemId);

        return new ResponseEntity<>(testCaseDtoSet, HttpStatus.OK);
    }

    @PutMapping("/test-cases/{id}")
    public ResponseEntity<TestCaseDto> updateTestCaseById(@PathVariable("id") Long testCaseId, @RequestBody UpdateTestCaseDto updateTestCaseDto){
        TestCaseDto updatedTestCase = testCaseService.updateTestCaseById(testCaseId, updateTestCaseDto);

        return new ResponseEntity<>(updatedTestCase, HttpStatus.OK);
    }

    @DeleteMapping("/test-cases/{id}")
    public ResponseEntity<String> deleteTestCaseById(@PathVariable("id") Long testCaseId){
        String message = testCaseService.deleteTestCase(testCaseId);

        return ResponseEntity.noContent().build();
    }
}
