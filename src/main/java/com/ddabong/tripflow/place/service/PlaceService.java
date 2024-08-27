package com.ddabong.tripflow.place.service;

import com.ddabong.tripflow.place.dao.IPlaceRepository;
import com.ddabong.tripflow.travel.dto.TravelPlaceJoinDTO;
import com.ddabong.tripflow.travel.model.TravelPlaceJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService implements IPlaceService{

    @Autowired
    private IPlaceRepository iPlaceRepository;
    @Override
    public Long getPlaceIdByTourPlaceName(String name) {
        return iPlaceRepository.getPlaceIdByTourPlaceName(name);
    }

    @Override
    public Long getPlaceIdByRestaurantPlaceName(String name) {
        return iPlaceRepository.getPlaceIdByRestaurantPlaceName(name);
    }

    @Override
    public void saveTravelPlace(TravelPlaceJoinDTO travelPlaceJoinDTO) {
        TravelPlaceJoin travelPlaceJoin = new TravelPlaceJoin();

        travelPlaceJoin.setPlaceId(travelPlaceJoinDTO.getPlaceId());
        travelPlaceJoin.setTravelId(travelPlaceJoinDTO.getTravelId());
        travelPlaceJoin.setDayNum(travelPlaceJoinDTO.getDayNum());
        travelPlaceJoin.setSequence(travelPlaceJoinDTO.getSequence());

        iPlaceRepository.saveTravelPlace(travelPlaceJoin);
    }

    @Override
    public Long getPlaceIdByHotelPlaceName(String name) {
        return iPlaceRepository.getPlaceIdByHotelPlaceName(name);
    }
}
