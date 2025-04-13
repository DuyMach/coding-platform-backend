package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ProblemController {
    private ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/problem")
    public ResponseEntity<ProblemDto> createProblem(@RequestBody ProblemDto problemDto){
        ProblemDto createdProblem = problemService.createProblem(problemDto);

        return new ResponseEntity<>(createdProblem, HttpStatus.CREATED);
    }

    @GetMapping("/problems")
    public ResponseEntity<Set<ProblemDto>> getAllProblem(){
        Set<ProblemDto> problemDtoSet = problemService.getAllProblem();

        return new ResponseEntity<>(problemDtoSet, HttpStatus.OK);
    }
}
