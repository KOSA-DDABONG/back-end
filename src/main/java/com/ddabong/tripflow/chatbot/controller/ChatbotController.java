package com.ddabong.tripflow.chatbot.controller;

import com.ddabong.tripflow.chatbot.dto.ChatbotDataResponseDTO;
import com.ddabong.tripflow.chatbot.dto.ResponseDTO;
import com.ddabong.tripflow.chatbot.dto.UserStateDTO;
import com.ddabong.tripflow.chatbot.service.IChatLogService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.ddabong.tripflow.place.model.Place;
import com.ddabong.tripflow.place.service.IPlaceService;
import com.ddabong.tripflow.travel.dto.TravelDTO;
import com.ddabong.tripflow.travel.dto.TravelPlaceJoinDTO;
import com.ddabong.tripflow.travel.dto.TravelSequenceSaveFormDTO;
import com.ddabong.tripflow.travel.service.ITravelService;
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
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IPlaceService placeService;

    private String flaskIP = "http://localhost:5000/";

    //private String chatting_state;

    private String chattingStartMessage = "안녕하세요!\n저는 당신만의 여행 플래너 TripFlow의 '립플'입니다.\n당신이 생각한 여행일정을 공유해주세요!";

    @Transactional
    @PostMapping("/start")
    public ResponseDTO chatBotStart(@RequestBody String startTime) {
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
            //chatting_state = responseBody; // 추후 DB테이블 관리

            System.out.println("USER 상태 초기화");

            UserStateDTO userStateDTO = initKeywords(jsonResponse, "", chattingStartMessage, userAge, userToken, startTime);

            chatbotDataResponseDTO.setChatbotMessage(chattingStartMessage);
            responseDTO.setMessage("Start Chatting");
            responseDTO.setStatus(200);
            responseDTO.setData(chatbotDataResponseDTO);

        } catch (Exception e){
            e.printStackTrace();
        }

        return responseDTO;
    }

    private UserStateDTO initKeywords(JsonNode jsonResponse, String userInput, String chatbotResponse, int userAge, Long userToken, String startTime) throws JsonProcessingException {
        UserStateDTO userStateDTO = new UserStateDTO(userInput, chatbotResponse, null, null, null, null, null, userAge, userToken, 0L, startTime);
        System.out.println("keyword 업데이트 시작" + jsonResponse.asText());
        // 응답이 JSON 문자열로 감싸진 경우 처리
        if (jsonResponse.has("question")) {
            System.out.println("user input : " + userInput);
            userStateDTO.setUserInput(userInput);
        }

        if (jsonResponse.has("keywords")) {
            ObjectNode keywordsNode = (ObjectNode) jsonResponse.get("keywords");
            System.out.println("keywords Json " + keywordsNode.asText());

            if(keywordsNode.has("days")){
                System.out.println("days 업데이트 : " + keywordsNode.get("days").asInt());
                if(keywordsNode.get("days").asText() != "null") {
                    userStateDTO.setDays(keywordsNode.get("days").asInt());
                }
            }
            if(keywordsNode.has("transport")){
                System.out.println("transport 업데이트" + keywordsNode.get("transport").asText());
                if(keywordsNode.get("transport").asText() != "null"){
                    userStateDTO.setTransport(keywordsNode.get("transport").asText());
                }
            }
            if(keywordsNode.has("companion")){
                System.out.println("companion 업데이트" + keywordsNode.get("companion").asText());
                if (keywordsNode.get("companion").asText() != "null"){
                    userStateDTO.setCompanion(keywordsNode.get("companion").asText());
                }
            }
            if(keywordsNode.has("theme")){
                System.out.println("theme 업데이트"+ keywordsNode.get("theme").asText());
                if (keywordsNode.get("theme").asText() != "null"){
                    userStateDTO.setTheme(keywordsNode.get("theme").asText());
                }
            }
            if(keywordsNode.has("food")){
                System.out.println("food 업데이트" + keywordsNode.get("food").asText());
                if (keywordsNode.get("food").asText() != "null"){
                    userStateDTO.setFood(keywordsNode.get("food").asText());
                }
            }

            chatLogService.initState(userStateDTO);
            System.out.println("user state DTO : " +userStateDTO);
        }

        return userStateDTO;
    }


    @Transactional
    @PostMapping("/conversation")
    public ResponseDTO makeSchedule(@RequestBody String userInput) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO("Enter Chatting room FAIL", 500, null);
        ChatbotDataResponseDTO chatbotDataResponseDTO = new ChatbotDataResponseDTO("","");

        try {
            System.out.println("유저 상태 불러오는 중");
            Long memberId = memberService.getMemberIdByUserId(getMemberInfoService.getUserIdByJWT());
            System.out.println("회원 ID: " + memberId);
            UserStateDTO userStateDTO = chatLogService.setUserState(memberId);

            String jsonString = "{\"question\": " + null + ", " +
                    "\"keywords\": {\"days\": " + null + ", \"transport\": " + null + ", \"companion\": " + null + ", \"theme\": " + null + ", \"food\": " + null + "}, " +
                    "\"foods_context\": [], \"playing_context\": [], \"hotel_context\": [], \"scheduler\": \"\", \"explain\": \"\", " +
                    "\"second_sentence\": \"\", \"user_age\": \"0\", \"user_token\": \"0\", \"is_valid\": 0}";

            userStateDTO.setUserInput(userInput);
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            ((ObjectNode) jsonNode).put("question", userInput);
            // keywords 객체를 추출
            System.out.println("키워드 객체 추출 중.... ");
            ObjectNode keywordsNode = (ObjectNode) jsonNode.get("keywords");
            keywordsNode.put("days", userStateDTO.getDays());
            keywordsNode.put("transport", userStateDTO.getTransport());
            keywordsNode.put("companion", userStateDTO.getCompanion());
            keywordsNode.put("theme", userStateDTO.getTheme());
            keywordsNode.put("food", userStateDTO.getFood());
            // 수정된 keywords 객체를 jsonNode에 다시 설정 (이 단계는 선택사항, 이미 참조로 수정됨)
            ((ObjectNode) jsonNode).set("keywords", keywordsNode);

            // 3. 업데이트된 JsonNode를 다시 JSON 문자열로 변환하여 chatting_state를 갱신
            jsonString = objectMapper.writeValueAsString(jsonNode);
            System.out.println("채팅 스테이트 업데이트 -----");
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

                updateKeyword(jsonResponse, userInput, responseText, userStateDTO.getAge(), userStateDTO.getToken());

                chatbotDataResponseDTO.setChatbotMessage(responseText);
                chatbotDataResponseDTO.setTravelSchedule("생성된 일정이 아직 없습니다.");
                responseDTO.setStatus(200);
                responseDTO.setMessage("Please Request Next User Input");
                responseDTO.setData(chatbotDataResponseDTO);
            } else {
                System.out.println("생성된 일정 ----------");
                updateKeyword(jsonResponse, userInput, "제가 추천해드리는 일정이에요! ^^", userStateDTO.getAge(), userStateDTO.getToken());
                System.out.println(responseBody);
                // 저장
                //saveSchedule(responseBody, userStateDTO.getStartTime(), userStateDTO.getToken());

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

    private void saveSchedule(String responseBody, String startTime, Long memberId) throws JsonProcessingException {
        JsonNode jsonResponse = objectMapper.readTree(responseBody);

        // JsonNode를 Map으로 변환
        Map<String, Object> jsonMap = objectMapper.convertValue(jsonResponse, Map.class);

        int date = 0;
        if(!jsonMap.isEmpty()){
            date = jsonMap.size();
        }

        System.out.println("여행 일정 저장");
        Long chatLogId = chatLogService.getChatLogId(memberId);
        Long curTravelId = saveTravelSchedule(memberId, startTime, date, chatLogId);

        System.out.println("일정 정리");
        List<TravelSequenceSaveFormDTO> travelSequences = new ArrayList<>();
        for(Map.Entry<String, Object> day : jsonMap.entrySet()){
            String key = day.getKey();
            String strDay = extractNumber(key);
            int dayNum = Integer.valueOf(strDay);
            int sequenceCnt = 1;

            Object value = day.getValue();
            System.out.println(dayNum + "일차------");
            System.out.println(value);

            Map<?, ?> scheduleMap = (Map<?, ?>) day.getValue();

            for(Map.Entry<?, ?> schedule : scheduleMap.entrySet()){
                System.out.println("KEY:" + schedule.getKey() + " VAL:" + schedule.getValue());
            }

            System.out.println("관광지 : " + scheduleMap.get("tourist_spots"));
            List<?> places = (List<?>) scheduleMap.get("tourist_spots");
            for(Object item : places){
                List<String> it = new ArrayList<>();
                it = (List<String>) item;
                String name = String.valueOf(it.get(0));
                //String strLatitude = it.get(1);
                //String strLongitude = it.get(2);

                //Double latitude = Double.parseDouble(strLatitude);
                //Double longitude = Double.parseDouble(strLongitude);

                saveTourPlace(name, sequenceCnt, curTravelId, dayNum);
                sequenceCnt += 1;

            }

            List<String> rit = new ArrayList<>();
            System.out.println("아침 : " + scheduleMap.get("breakfast"));
            rit = (List<String>) scheduleMap.get("breakfast");
            String breakFastName = String.valueOf(rit.get(0));
            //String strbreakFastLatitude = rit.get(1);
            //String strbreakFastLongitude = rit.get(2);
            //Double breakFastLatitude = Double.parseDouble(strbreakFastLatitude);
            //Double breakFastLongitude = Double.parseDouble(strbreakFastLongitude);

            saveRestaurantPlace(breakFastName, sequenceCnt, curTravelId, dayNum);
            sequenceCnt += 1;


            System.out.println("점심 : " + scheduleMap.get("lunch"));
            rit.clear();
            rit = (List<String>) scheduleMap.get("lunch");
            String lunchName = String.valueOf(rit.get(0));
            //String strLunchLatitude = rit.get(1);
            //String strLunchLongitude = rit.get(2);
            //Double lunchLatitude = Double.parseDouble(strLunchLatitude);
            //Double lunchLongitude = Double.parseDouble(strLunchLongitude);

            saveRestaurantPlace(lunchName, sequenceCnt, curTravelId, dayNum);
            sequenceCnt += 1;

            System.out.println("저녁 : " + scheduleMap.get("dinner"));
            rit.clear();
            rit = (List<String>) scheduleMap.get("dinner");
            String dinnerName = String.valueOf(rit.get(0));
            //String strDinnerLatitude = rit.get(1);
            //String strDinnerLongitude = rit.get(2);
            //Double dinnerLatitude = Double.parseDouble(strDinnerLatitude);
            //Double dinnerLongitude = Double.parseDouble(strDinnerLongitude);

            saveRestaurantPlace(dinnerName, sequenceCnt, curTravelId, dayNum);
            sequenceCnt += 1;

            if(scheduleMap.get("hotel") != null){
                System.out.println("숙소 : " + scheduleMap.get("hotel"));
                rit.clear();
                rit = (List<String>) scheduleMap.get("hotel");
                String hotelName = rit.get(0);
                //String strHotelLatitude = rit.get(1);
                //String strHotelLongitude = rit.get(2);
                //Double hotelLatitude = Double.parseDouble(strHotelLatitude);
                //Double hotelLongitude = Double.parseDouble(strHotelLongitude);

                saveHotelPlace(hotelName, sequenceCnt, curTravelId, dayNum);
                sequenceCnt += 1;
            }

        }

    }

    private void saveTourPlace(String name, int sequenceCnt, Long travelId, int dayNum) {
        Long placeId = placeService.getPlaceIdByTourPlaceName(name);

        TravelPlaceJoinDTO travelPlaceJoinDTO = new TravelPlaceJoinDTO();
        travelPlaceJoinDTO.setPlaceId(placeId);
        travelPlaceJoinDTO.setTravelId(travelId);
        travelPlaceJoinDTO.setDayNum(dayNum);
        travelPlaceJoinDTO.setSequence(sequenceCnt);
        System.out.println("여행 장소 JOIN : " + travelPlaceJoinDTO);
        System.out.println("장소 id : " + placeId + " 장소 이름 : " + name);

        placeService.saveTravelPlace(travelPlaceJoinDTO);
    }
    private void saveHotelPlace(String name, int sequenceCnt, Long travelId, int dayNum) {
        Long placeId = placeService.getPlaceIdByHotelPlaceName(name);

        TravelPlaceJoinDTO travelPlaceJoinDTO = new TravelPlaceJoinDTO();
        travelPlaceJoinDTO.setPlaceId(placeId);
        travelPlaceJoinDTO.setTravelId(travelId);
        travelPlaceJoinDTO.setDayNum(dayNum);
        travelPlaceJoinDTO.setSequence(sequenceCnt);
        System.out.println("여행 장소 JOIN : " + travelPlaceJoinDTO);
        System.out.println("장소 id : " + placeId + " 장소 이름 : " + name);

        placeService.saveTravelPlace(travelPlaceJoinDTO);
    }
    private void saveRestaurantPlace(String name, int sequenceCnt, Long travelId, int dayNum) {
        Long placeId = placeService.getPlaceIdByRestaurantPlaceName(name);

        TravelPlaceJoinDTO travelPlaceJoinDTO = new TravelPlaceJoinDTO();
        travelPlaceJoinDTO.setPlaceId(placeId);
        travelPlaceJoinDTO.setTravelId(travelId);
        travelPlaceJoinDTO.setDayNum(dayNum);
        travelPlaceJoinDTO.setSequence(sequenceCnt);
        System.out.println("여행 장소 JOIN : " + travelPlaceJoinDTO);
        System.out.println("장소 id : " + placeId + " 장소 이름 : " + name);

        placeService.saveTravelPlace(travelPlaceJoinDTO);
    }

    private Long saveTravelSchedule(Long memberId, String startTime, int date, Long chatLogId) {
        return travelService.saveTravelSchedule(memberId, startTime, date, chatLogId);
    }

    private String extractNumber(String key) {
        return key.replaceAll("\\D+", "");
    }


    private UserStateDTO updateKeyword(JsonNode jsonResponse, String userInput, String chatbotResponse, int userAge, Long userToken) throws JsonProcessingException {
        UserStateDTO userStateDTO = new UserStateDTO(userInput, chatbotResponse, null, null, null, null, null, userAge, userToken, 0L, null);
        System.out.println("keyword 업데이트 시작" + jsonResponse.asText());
        // 응답이 JSON 문자열로 감싸진 경우 처리
        if (jsonResponse.has("question")) {
            System.out.println("user input : " + userInput);
            userStateDTO.setUserInput(userInput);
        }

        if (jsonResponse.has("keywords")) {
            ObjectNode keywordsNode = (ObjectNode) jsonResponse.get("keywords");
            System.out.println("keywords Json " + keywordsNode.asText());

            if(keywordsNode.has("days")){
                System.out.println("days 업데이트 : " + keywordsNode.get("days").asInt());
                if(keywordsNode.get("days").asText() != "null") {
                    userStateDTO.setDays(keywordsNode.get("days").asInt());
                }
            }
            if(keywordsNode.has("transport")){
                System.out.println("transport 업데이트" + keywordsNode.get("transport").asText());
                if(keywordsNode.get("transport").asText() != "null"){
                    userStateDTO.setTransport(keywordsNode.get("transport").asText());
                }
            }
            if(keywordsNode.has("companion")){
                System.out.println("companion 업데이트" + keywordsNode.get("companion").asText());
                if (keywordsNode.get("companion").asText() != "null"){
                    userStateDTO.setCompanion(keywordsNode.get("companion").asText());
                }
            }
            if(keywordsNode.has("theme")){
                System.out.println("theme 업데이트"+ keywordsNode.get("theme").asText());
                if (keywordsNode.get("theme").asText() != "null"){
                    userStateDTO.setTheme(keywordsNode.get("theme").asText());
                }
            }
            if(keywordsNode.has("food")){
                System.out.println("food 업데이트" + keywordsNode.get("food").asText());
                if (keywordsNode.get("food").asText() != "null"){
                    userStateDTO.setFood(keywordsNode.get("food").asText());
                }
            }

            chatLogService.updateState(userStateDTO);
            System.out.println("user state DTO : " + userStateDTO);
        }

        return userStateDTO;
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
