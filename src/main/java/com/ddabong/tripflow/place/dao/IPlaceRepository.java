package com.ddabong.tripflow.place.dao;

import com.ddabong.tripflow.travel.model.TravelPlaceJoin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPlaceRepository {

    Long getPlaceIdByTourPlaceName(String name);

    Long getPlaceIdByRestaurantPlaceName(String name);

    void saveTravelPlace(TravelPlaceJoin travelPlaceJoin);

    Long getPlaceIdByHotelPlaceName(String name);
}
