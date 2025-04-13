package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.RoleDto;
import com.codingplatform.coding_platform_backend.dto.UserEntityDto;
import com.codingplatform.coding_platform_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserEntityController {
    private UserService userService;

    @Autowired
    public UserEntityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntityDto>> getAllUser(){
        List<UserEntityDto> userEntityDtoList = userService.getAllUser();

        return new ResponseEntity<>(userEntityDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/roles")
    public ResponseEntity<Set<RoleDto>> getRolesByUserId(@PathVariable("id") Long userId){
        Set<RoleDto> roleDtoSet = userService.getRolesByUserId(userId);
        return new ResponseEntity<>(roleDtoSet, HttpStatus.OK);
    }

    @PutMapping("/user/{id}/role/{roleId}")
    public ResponseEntity<String> addRoleToUser(@PathVariable("id") Long userId,@PathVariable("roleId") Long roleId){
        String message = userService.addRoleToUser(userId, roleId);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
