package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.UserEntityDto;
import com.codingplatform.coding_platform_backend.models.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserEntityMapper {
    public static UserEntityDto mapToUserDto(UserEntity user){
        return new UserEntityDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreationDate()
        );
    }

    public static List<UserEntityDto> mapToUserDtoList(List<UserEntity> userEntityList){
        return userEntityList.stream().map(UserEntityMapper::mapToUserDto).collect(Collectors.toList());
    }
}
