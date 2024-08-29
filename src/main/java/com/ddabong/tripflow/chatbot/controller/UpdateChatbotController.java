package com.ddabong.tripflow.chatbot.controller;


import com.ddabong.tripflow.chatbot.dto.*;
import com.ddabong.tripflow.chatbot.service.IChatLogService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.apache.http.client.methods.HttpHead;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class UpdateChatbotController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IChatLogService chatLogService;

    private String flaskIP = "http://localhost:5000/";

    @Transactional
    @PostMapping("/updateByChat/{id}")
    public ResponseDTO updateScheduleDTO(@PathVariable("id") Long travelId, @RequestBody String userInput){
        ChatbotDataResponseDTO chatbotDataResponseDTO = new ChatbotDataResponseDTO("","");
        ResponseDTO responseDTO = new ResponseDTO("Loading...", 500, chatbotDataResponseDTO);
        try{
            System.out.println("사용자 수정요청 서버에 전송 중...");
            String userId = getMemberInfoService.getUserIdByJWT();
            Long memberId = memberService.getMemberIdByUserId(userId);
            System.out.println("member Id : " + memberId);
            UpdateUserStateDTO updateUserStateDTO = chatLogService.setUpdateUserState(travelId);
            System.out.println("사용자 입력 : " + updateUserStateDTO.getUserInput());
            System.out.println("봇 응답 : " + updateUserStateDTO.getBotResponse());
            System.out.println("여행 총 일수 : " + updateUserStateDTO.getDays());
            System.out.println("대중교통 : " + updateUserStateDTO.getTransport());
            System.out.println("동행자 : " + updateUserStateDTO.getCompanion());
            System.out.println("테마 : " + updateUserStateDTO.getTheme());
            System.out.println("음식 : " + updateUserStateDTO.getFood());
            System.out.println("나이 : " + updateUserStateDTO.getAge());
            System.out.println("member id : " + updateUserStateDTO.getToken());
            System.out.println("과거 채팅 id : " + updateUserStateDTO.getPastChatId());
            System.out.println("일정 시작 날짜 : " + updateUserStateDTO.getStartTime());
            System.out.println("일정 : " + updateUserStateDTO.getScheduler());
            System.out.println("음식 목록 : " + updateUserStateDTO.getFoodsContext());
            System.out.println("관광지 목록 : " + updateUserStateDTO.getPlayingContext());
            System.out.println("숙박 목록 : " + updateUserStateDTO.getHotelContext());
            System.out.println("설명 : " + updateUserStateDTO.getExplain());
            System.out.println("validation 질의 : " + updateUserStateDTO.getSecondSentence());
            System.out.println("is valid : " + updateUserStateDTO.getIsValid());
            System.out.println("message : " + updateUserStateDTO.getMessage());


            String jsonString = "{\"question\": " + null + ", " +
                    "\"keywords\": {\"days\": " + null + ", \"transport\": " + null + ", \"companion\": " + null + ", \"theme\": " + null + ", \"food\": " + null + "}, " +
                    "\"foods_context\": [], \"playing_context\": [], \"hotel_context\": [], \"scheduler\": \"\", \"explain\": \"\", " +
                    "\"second_sentence\": \"\", \"user_age\": \"0\", \"user_token\": \"0\", \"is_valid\": 0, \"message\":\"\"}";

            System.out.println("사용자 수정 내용 : " + userInput);
            updateUserStateDTO.setMessage(userInput);
            System.out.println("Message 세팅결과 : " + updateUserStateDTO.getMessage());
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            ((ObjectNode) jsonNode).put("message", userInput);
            // keywords 객체를 추출
            System.out.println("키워드 객체 추출 중.... ");
            ObjectNode keywordsNode = (ObjectNode) jsonNode.get("keywords");
            keywordsNode.put("days", updateUserStateDTO.getDays());
            keywordsNode.put("transport", updateUserStateDTO.getTransport());
            keywordsNode.put("companion", updateUserStateDTO.getCompanion());
            keywordsNode.put("theme", updateUserStateDTO.getTheme());
            keywordsNode.put("food", updateUserStateDTO.getFood());
            // 수정된 keywords 객체를 jsonNode에 다시 설정 (이 단계는 선택사항, 이미 참조로 수정됨)
            ((ObjectNode) jsonNode).set("keywords", keywordsNode);

            ((ObjectNode) jsonNode).put("foods_context", updateUserStateDTO.getFoodsContext());
            ((ObjectNode) jsonNode).put("playing_context", updateUserStateDTO.getPlayingContext());
            ((ObjectNode) jsonNode).put("hotel_context", updateUserStateDTO.getHotelContext());
            ((ObjectNode) jsonNode).put("scheduler", updateUserStateDTO.getScheduler());
            ((ObjectNode) jsonNode).put("explain", updateUserStateDTO.getExplain());
            ((ObjectNode) jsonNode).put("second_sentence", updateUserStateDTO.getSecondSentence());
            ((ObjectNode) jsonNode).put("user_age", updateUserStateDTO.getAge());
            ((ObjectNode) jsonNode).put("user_token", updateUserStateDTO.getToken());
            ((ObjectNode) jsonNode).put("is_valid", updateUserStateDTO.getIsValid());
            ((ObjectNode) jsonNode).put("message", updateUserStateDTO.getMessage());

            // 3. 업데이트된 JsonNode를 다시 JSON 문자열로 변환하여 chatting_state를 갱신
            jsonString = objectMapper.writeValueAsString(jsonNode);
            System.out.println("채팅 스테이트 업데이트 -----");
            System.out.println(jsonNode);

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 엔터티 생성
            HttpEntity<String> request = new HttpEntity<>(jsonString, headers);

            // Flask API로 POST 요청 보내기
            System.out.println("Flask 요청 시작");
            String flaskApiUrl = flaskIP + "updating_place";
            ResponseEntity<String> response = restTemplate.exchange(flaskApiUrl, HttpMethod.POST, request, String.class);
            System.out.println("Flask의 응답 : " + response.getBody());

            // Flask에서 받은 응답을 JSON 형태로 변환
            String responseBody = response.getBody();
            //String responseBody = new String(response.getBody().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            System.out.println("1");
            JsonNode jsonResponse = objectMapper.readTree(responseBody);


        }catch(Exception e){

        }

        return responseDTO;
    }

    private int getUserAge(String userId) {
        String userBirth = memberService.getBirthByUserId(userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(userBirth, formatter);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }
}
