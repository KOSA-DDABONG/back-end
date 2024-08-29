package com.ddabong.tripflow.travel.service;

import com.ddabong.tripflow.travel.dto.TravelDTO;
import com.ddabong.tripflow.travel.model.Travel;

import java.util.List;

public interface ITravelService {
    TravelDTO getTravelInfoByTravelId(Long travelId);

    Long saveTravelSchedule(Long memberId, String startTime, int date, Long chatLogId);

    List<TravelDTO> loadPastTravelList(Long memberId);

    List<TravelDTO> loadFutureTravelList(Long memberId);

    List<TravelDTO> loadPresentTravelList(Long memberId);
}
