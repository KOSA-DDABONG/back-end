package com.ddabong.tripflow.place.service;

import com.ddabong.tripflow.place.dto.PlaceDTO;
import com.ddabong.tripflow.travel.dto.TravelPlaceJoinDTO;

public interface IPlaceService {
    Long getPlaceIdByTourPlaceName(String name);

    Long getPlaceIdByRestaurantPlaceName(String breakFastName);

    void saveTravelPlace(TravelPlaceJoinDTO travelPlaceJoinDTO);

    Long getPlaceIdByHotelPlaceName(String name);

    PlaceDTO getPlaceInfoByPlaceId(Long placeId);
}
