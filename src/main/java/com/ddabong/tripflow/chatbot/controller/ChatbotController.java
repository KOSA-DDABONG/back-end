package com.ddabong.tripflow.chatbot.controller;

import com.ddabong.tripflow.chatbot.dto.ChatbotDataResponseDTO;
import com.ddabong.tripflow.chatbot.dto.ResponseDTO;
import com.ddabong.tripflow.chatbot.dto.UserStateDTO;
import com.ddabong.tripflow.chatbot.service.IChatLogService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatbotController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IChatLogService chatLogService;

    private String flaskIP = "http://localhost:5000/";

    private String chatting_state;

    private String chattingStartMessage = "안녕하세요!\n저는 당신만의 여행 플래너 TripFlow의 '립플'입니다.\n당신이 생각한 여행일정을 공유해주세요!";

    @Transactional
    @GetMapping("/start")
    public ResponseDTO chatBotStart() {
        ResponseDTO responseDTO = new ResponseDTO("Enter Chatting room FAIL", 500, null);
        ChatbotDataResponseDTO chatbotDataResponseDTO = new ChatbotDataResponseDTO("","");

        System.out.println("채팅 준비 ----------------");
        try {
            System.out.println("유저 정보 생성");
            String userId = getMemberInfoService.getUserIdByJWT();
            Long userToken = memberService.getMemberIdByUserId(userId);
            int userAge = getUserAge(userId);

            System.out.println("헤더 생성");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("userAge", userAge);
            requestBody.put("userToken", userToken);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            String flaskUrl = flaskIP + "get_user?userAge=" + userAge + "&userToken=" + userToken;
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);

            // Flask에서 받은 응답을 JSON 형태로 변환
            String responseBody = response.getBody();
            JsonNode jsonResponse = objectMapper.readTree(responseBody);

            System.out.println("플라스크가 보내준 responseBody ----------");
            System.out.println(responseBody);
            chatting_state = responseBody; // 추후 DB테이블 관리

            System.out.println("USER 상태 초기화");

            UserStateDTO userStateDTO = keywordUpdate(jsonResponse, userAge, userToken);

            chatbotDataResponseDTO.setChatbotMessage(chattingStartMessage);
            responseDTO.setMessage("Start Chatting");
            responseDTO.setStatus(200);
            responseDTO.setData(chatbotDataResponseDTO);

        } catch (Exception e){
            e.printStackTrace();
        }

        return responseDTO;
    }

    private UserStateDTO keywordUpdate(JsonNode jsonResponse, int userAge, Long userToken) throws JsonProcessingException {
        UserStateDTO userStateDTO = new UserStateDTO("", chattingStartMessage, 0, null, null, null, null, userAge, userToken, 0L);
        // 응답이 JSON 문자열로 감싸진 경우 처리
        if (jsonResponse.has("keywords")) {
            String responseText = jsonResponse.get("keywords").asText();
            JsonNode keywordsJson = objectMapper.readTree(responseText);
            //return ResponseEntity.ok(responseJson);

            if(keywordsJson.has("days")){
                System.out.println("days 업데이트");
                userStateDTO.setDays(keywordsJson.get("days").asInt());
                System.out.println("days : " + userStateDTO.getDays());
            }
            if(keywordsJson.has("transport")){
                System.out.println("transport 업데이트");
                userStateDTO.setTransport(keywordsJson.get("transport").asText());
                System.out.println("transport : " + userStateDTO.getTransport());
            }
            if(keywordsJson.has("companion")){
                System.out.println("companion 업데이트");
                userStateDTO.setCompanion(keywordsJson.get("companion").asText());
                System.out.println("companion : " + userStateDTO.getCompanion());
            }
            if(keywordsJson.has("theme")){
                System.out.println("theme 업데이트");
                userStateDTO.setTheme(keywordsJson.get("theme").asText());
                System.out.println("theme : " + userStateDTO.getTheme());
            }
            if(keywordsJson.has("food")){
                System.out.println("food 업데이트");
                userStateDTO.setFood(keywordsJson.get("food").asText());
                System.out.println("food : " + userStateDTO.getFood());
            }

            chatLogService.saveState(userStateDTO);
        }

        return userStateDTO;
    }


    @Transactional
    @PostMapping("/conversation")
    public ResponseDTO makeSchedule(@RequestBody String userInput) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO("Enter Chatting room FAIL", 500, null);
        ChatbotDataResponseDTO chatbotDataResponseDTO = new ChatbotDataResponseDTO("","");

        try {
            String jsonString = chatting_state;
            System.out.println("채팅 스테이트 변경 전 -----");
            System.out.println(chatting_state);
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            ((ObjectNode) jsonNode).put("question", userInput);
            // 3. 업데이트된 JsonNode를 다시 JSON 문자열로 변환하여 chatting_state를 갱신
            chatting_state = objectMapper.writeValueAsString(jsonNode);
            jsonString = chatting_state;
            System.out.println("채팅 스테이트 변경 후 -----");
            System.out.println(jsonNode); // {"question":"4일 정도 여행계획이 있고, 부모님과 자차로 이동할거야. 주로 관광지와 먹거리를 먹으러 돌아다닐거고, 따로 가리는 음식은 없어.","keywords":{"days":null,"transport":null,"companion":null,"theme":null,"food":null},"foods_context":[],"playing_context":[],"hotel_context":[],"scheduler":"","explain":"","second_sentence":"","user_age":"27","user_token":"3","is_valid":0}

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 엔터티 생성
            HttpEntity<String> request = new HttpEntity<>(jsonString, headers);

            // Flask API로 POST 요청 보내기
            System.out.println("Flask 요청 시작");
            String flaskApiUrl = "http://localhost:5000/making";
            ResponseEntity<String> response = restTemplate.exchange(flaskApiUrl, HttpMethod.POST, request, String.class);
            System.out.println("Flask의 응답 : " + response.getBody());

            // Flask에서 받은 응답을 JSON 형태로 변환
            String responseBody = response.getBody();
            JsonNode jsonResponse = objectMapper.readTree(responseBody);


            // 응답이 JSON 문자열로 감싸진 경우 처리
            if (jsonResponse.has("response")) {
                String responseText = jsonResponse.get("response").asText();
                //JsonNode responseJson = objectMapper.readTree(responseText);
                //return ResponseEntity.ok(responseJson);

                System.out.println("챗봇 응담 >>>>>>>");
                System.out.println(responseText);
                chatting_state = responseBody; // 추후 DB테이블 관리

                chatbotDataResponseDTO.setChatbotMessage(responseText);
                chatbotDataResponseDTO.setTravelSchedule("생성된 일정이 아직 없습니다.");
                responseDTO.setStatus(200);
                responseDTO.setMessage("Please Request Next User Input");
                responseDTO.setData(chatbotDataResponseDTO);
            } else {
                System.out.println("생성된 일정 ----------");
                System.out.println(responseBody);
                chatting_state = responseBody; // 추후 DB테이블 관리

                chatbotDataResponseDTO.setChatbotMessage("생성된 일정이 마음에 드시나요?");
                chatbotDataResponseDTO.setTravelSchedule(responseBody);
                responseDTO.setStatus(200);
                responseDTO.setMessage("Please Request IsValid Input");
                responseDTO.setData(chatbotDataResponseDTO);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
        // Flask의 응답 반환
        return responseDTO;
    }



    @PostMapping("/userResponse")
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

    private int getUserAge(String userId) {
        String userBirth = memberService.getBirthByUserId(userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(userBirth, formatter);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }

}
