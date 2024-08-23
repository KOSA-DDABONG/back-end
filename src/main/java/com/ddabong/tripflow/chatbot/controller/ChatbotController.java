package com.ddabong.tripflow.chatbot.controller;

import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
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

import javax.swing.plaf.IconUIResource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private IMemberService memberService;

    @PostMapping("/make-schedule")
    public ResponseEntity<JsonNode> makeSchedule(@RequestBody Map<String, String> request) throws IOException {
        String userId = getMemberInfoService.getUserIdByJWT();
        String userBirth = memberService.getBirthByUserId(userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(userBirth, formatter);
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        String question = request.get("question");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("question", question);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String flaskUrl = "http://localhost:5000/making?userAge=" + age;
        ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);

        // Flask에서 받은 응답을 JSON 형태로 변환
        String responseBody = response.getBody();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        System.out.println("responseBody ----------");
        System.out.println(responseBody);

        // 응답이 JSON 문자열로 감싸진 경우 처리
        if (jsonResponse.has("response")) {
            String responseText = jsonResponse.get("response").asText();
            JsonNode responseJson = objectMapper.readTree(responseText);
            return ResponseEntity.ok(responseJson);
        }

        System.out.println("챗봇 응담 >>>>>>>");
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
