package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.StarterCodeDto;
import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import com.codingplatform.coding_platform_backend.service.StarterCodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StarterCodeController {
    private StarterCodeService starterCodeService;

    @Autowired
    public StarterCodeController(StarterCodeService starterCodeService) {
        this.starterCodeService = starterCodeService;
    }

    @PostMapping("/problems/{id}/starter-codes")
    public ResponseEntity<StarterCodeDto> createStarterCodeByProblemId(@PathVariable("id") Long problemId, @Valid @RequestBody StarterCodeDto starterCodeDto){
        StarterCodeDto starterCode = starterCodeService.createStarterCodeByProblemId(problemId, starterCodeDto);

        return new ResponseEntity<>(starterCode, HttpStatus.CREATED);
    }

    @GetMapping("/problems/{id}/starter-codes/{language}")
    public ResponseEntity<StarterCodeDto> getStarterCodeByProblemIdAndLanguage(
            @PathVariable("id") Long problemId,
            @PathVariable("language") LanguageName languageName)
    {
        StarterCodeDto starterCodeDto = starterCodeService.getStarterCodeByProblemIdAndLanguage(problemId, languageName);

        return new ResponseEntity<>(starterCodeDto, HttpStatus.OK);
    }
}
