package com.codingplatform.coding_platform_backend.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class HintListConverter implements AttributeConverter<List<String>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> hintList) {
        try {
            return objectMapper.writeValueAsString(hintList);
        } catch (Exception e){
            throw new IllegalArgumentException("Failed to convert hint list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String hintJson) {
        try {
            return objectMapper.readValue(hintJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert JSON to hint list", e);
        }
    }
}
