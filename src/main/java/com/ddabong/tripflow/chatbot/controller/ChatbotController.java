package com.ddabong.tripflow.chatbot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/make-schedule")
    public ResponseEntity<JsonNode> makeSchedule(@RequestBody Map<String, String> request) throws IOException {
        String question = request.get("question");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("question", question);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String flaskUrl = "http://localhost:5000/making";
        ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);

        // Flask에서 받은 응답을 JSON 형태로 변환
        String responseBody = response.getBody();

        // 유니코드 이스케이프를 디코딩하여 JsonNode로 변환
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        // 'response' 필드의 값을 다시 파싱하여 JsonNode로 변환
        if (jsonResponse.has("response")) {
            JsonNode responseJson = objectMapper.readTree(jsonResponse.get("response").asText());
            return ResponseEntity.ok(responseJson);
        }

        System.out.println("챗봇 응답 >>>>>>>> ");
        System.out.println(jsonResponse);
        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping("/validate-schedule")
    public ResponseEntity<JsonNode> validateSchedule(@RequestBody Map<String, String> request) throws IOException {
        String question = request.get("question");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("question", question);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String flaskUrl = "http://localhost:5000/validating";
        ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);

        // Flask에서 받은 응답을 JSON 형태로 변환
        String responseBody = response.getBody();

        // 유니코드 이스케이프를 디코딩하여 JsonNode로 변환
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        // 'response' 필드의 값을 다시 파싱하여 JsonNode로 변환
        if (jsonResponse.has("response")) {
            JsonNode responseJson = objectMapper.readTree(jsonResponse.get("response").asText());
            return ResponseEntity.ok(responseJson);
        }

        return ResponseEntity.ok(jsonResponse);
    }
}
