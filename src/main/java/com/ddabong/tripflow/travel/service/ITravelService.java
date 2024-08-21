package com.ddabong.tripflow.travel.service;

import com.ddabong.tripflow.travel.dto.TravelDTO;
import com.ddabong.tripflow.travel.model.Travel;

public interface ITravelService {
    TravelDTO getTravelInfoByTravelId(Long travelId);
}
