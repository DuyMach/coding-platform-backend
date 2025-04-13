package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.TagDto;

import java.util.Set;

public interface TagService {
    Set<TagDto> getAllTag();
}
