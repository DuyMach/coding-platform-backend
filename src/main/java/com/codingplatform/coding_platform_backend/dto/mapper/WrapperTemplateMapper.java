package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.WrapperTemplateDto;
import com.codingplatform.coding_platform_backend.models.WrapperTemplate;


public class WrapperTemplateMapper {
    public static WrapperTemplateDto mapToDto(WrapperTemplate wrapperTemplate){
        return new WrapperTemplateDto(
                wrapperTemplate.getId(),
                wrapperTemplate.getLanguage(),
                wrapperTemplate.getCode(),
                wrapperTemplate.getProblem().getId()
        );
    }

    public static WrapperTemplate mapToEntity(WrapperTemplateDto wrapperTemplateDto){
        WrapperTemplate wrapperTemplate = new WrapperTemplate();
        wrapperTemplate.setLanguage(wrapperTemplateDto.getLanguage());
        wrapperTemplate.setCode(wrapperTemplateDto.getCode());

        return wrapperTemplate;
    }
}
