package com.codingplatform.coding_platform_backend.dto;

import com.codingplatform.coding_platform_backend.models.enums.TagName;
import lombok.Data;

@Data
public class TagDto {
    private Long id;
    private TagName name;
}
