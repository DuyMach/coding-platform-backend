package com.codingplatform.coding_platform_backend.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter
public class ConstraintListConverter implements AttributeConverter<List<String>, String> {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> constraintList) {
        try {
            return objectMapper.writeValueAsString(constraintList);
        } catch (Exception e){
            throw new IllegalArgumentException("Failed to convert constraint list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String constraintJson) {
        try {
            return objectMapper.readValue(constraintJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert JSON to constraint list", e);
        }
    }
}
