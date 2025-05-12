package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.WrapperTemplateDto;
import com.codingplatform.coding_platform_backend.service.WrapperTemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WrapperTemplateController {
    private WrapperTemplateService wrapperTemplateService;

    @Autowired
    public WrapperTemplateController(WrapperTemplateService wrapperTemplateService) {
        this.wrapperTemplateService = wrapperTemplateService;
    }

    @PostMapping("/problems/{id}/wrapper-template")
    public ResponseEntity<WrapperTemplateDto> createWrapperTemplateByProblemId(
            @PathVariable("id") Long problemId,
            @Valid @RequestBody WrapperTemplateDto wrapperTemplateDto)
    {
        WrapperTemplateDto result = wrapperTemplateService.createWrapperTemplateByProblemId(problemId, wrapperTemplateDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
