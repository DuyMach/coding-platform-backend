package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.StarterCodeDto;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.StarterCode;

import java.util.Set;
import java.util.stream.Collectors;

public class StarterCodeMapper {
    public static StarterCodeDto mapToDto(StarterCode starterCode){
        return new StarterCodeDto(
                starterCode.getId(),
                starterCode.getLanguage(),
                starterCode.getCode(),
                starterCode.getProblem().getId()
        );
    }

    public static StarterCode mapToEntity(StarterCodeDto starterCodeDto){
        StarterCode starterCode = new StarterCode();
        starterCode.setLanguage(starterCodeDto.getLanguage());
        starterCode.setCode(starterCodeDto.getCode());

        return starterCode;
    }

    public static Set<StarterCodeDto> mapToDtoSet(Set<StarterCode> starterCodeSet){
        return starterCodeSet.stream().map(StarterCodeMapper::mapToDto).collect(Collectors.toSet());
    }
}
