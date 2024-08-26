package com.ddabong.tripflow.travel.service;

import com.ddabong.tripflow.travel.dao.ITravelRepository;
import com.ddabong.tripflow.travel.dto.TravelDTO;
import com.ddabong.tripflow.travel.model.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        travelDTO.setChatLogId(travel.getChatlogid());

        return travelDTO;
    }
}
