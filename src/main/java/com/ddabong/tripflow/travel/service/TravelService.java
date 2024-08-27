package com.ddabong.tripflow.travel.service;

import com.ddabong.tripflow.travel.dao.ITravelRepository;
import com.ddabong.tripflow.travel.dto.TravelDTO;
import com.ddabong.tripflow.travel.model.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TravelService implements ITravelService{
    @Autowired
    private ITravelRepository iTravelRepository;

    @Override
    public TravelDTO getTravelInfoByTravelId(Long travelId) {
        Travel travel = iTravelRepository.getTravelInfoByTravelId(travelId);

        TravelDTO travelDTO = new TravelDTO();
        travelDTO.setTravelId(travel.getTravelId());
        travelDTO.setMemberId(travel.getMemberId());
        travelDTO.setCreatedTime(travel.getCreatedTime());
        travelDTO.setStartTime(travel.getStartTime());
        travelDTO.setEndTime(travel.getEndTime());
        travelDTO.setChatLogId(travel.getChatLogId());

        return travelDTO;
    }

    @Override
    public Long saveTravelSchedule(Long memberId, String startTime, int date, Long chatLogId) {
        Travel travel = new Travel();

        travel.setMemberId(memberId);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        travel.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));

        travel.setStartTime(startTime);
        travel.setEndTime(getEndTime(startTime, date));
        travel.setChatLogId(chatLogId);

        iTravelRepository.saveTravelSchedule(travel);
        return travel.getTravelId();
    }

    private String getEndTime(String startTime, int date){
        // 날짜 형식 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 시작 날짜 설정
        LocalDate startDate = LocalDate.parse(startTime, formatter);
        // 4일 추가
        LocalDate newDate = startDate.plusDays(date);
        // 결과 출력
        String endTime = newDate.format(formatter);
        return endTime;
    }
}
