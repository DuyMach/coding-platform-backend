package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.Judge0SubmissionRequest;
import com.codingplatform.coding_platform_backend.dto.Judge0SubmissionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class Judge0Client {
    @Value("${judge0.api.url}")
    private String judge0ApiUrl;

    @Value("${judge0.api.key}")
    private String apiKey;

    @Value("${judge0.api.host}")
    private String apiHost;

    public final RestTemplate restTemplate = new RestTemplate();

    public Judge0SubmissionResponse submitCode(Judge0SubmissionRequest request){
        String json;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(request);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-rapidapi-host", apiHost);
            headers.set("x-rapidapi-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            ResponseEntity<Judge0SubmissionResponse> response = restTemplate.exchange(
                    judge0ApiUrl + "/submissions?base64_encoded=false&wait=true&fields=*",
                    HttpMethod.POST,
                    entity,
                    Judge0SubmissionResponse.class
            );

            System.out.println("Response: ");
            System.out.println(mapper.writeValueAsString(response));
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize Judge0 request", e);
        }

    }

}
