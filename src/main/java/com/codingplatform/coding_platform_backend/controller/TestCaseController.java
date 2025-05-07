package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.TestCaseDto;
import com.codingplatform.coding_platform_backend.service.TestCaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
