package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.RoleDto;
import com.codingplatform.coding_platform_backend.dto.UserEntityDto;
import com.codingplatform.coding_platform_backend.dto.mapper.RoleMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.UserEntityMapper;
import com.codingplatform.coding_platform_backend.exception.RoleNotFoundException;
import com.codingplatform.coding_platform_backend.exception.UserNotFoundException;
import com.codingplatform.coding_platform_backend.models.Role;
import com.codingplatform.coding_platform_backend.models.UserEntity;
import com.codingplatform.coding_platform_backend.repository.RoleRepository;
import com.codingplatform.coding_platform_backend.repository.UserRepository;
import com.codingplatform.coding_platform_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserEntityDto> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        return UserEntityMapper.mapToUserDtoList(userEntities);
    }

    @Override
    public Set<RoleDto> getRolesByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        Set<Role> roles = user.getRoles();

        return RoleMapper.mapToRoleDtoList(roles);
    }

    @Override
    public String addRoleToUser(Long userId, Long roleId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role does not exist!"));

        boolean isValid = user.addRole(role);
        if(isValid){
            userRepository.save(user);
            return "Successfully added " + role.getName().toString() + " role!";
        }

        return "User already have that role!";
    }


}
