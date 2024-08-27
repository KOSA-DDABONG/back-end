package com.ddabong.tripflow.travel.dao;

import com.ddabong.tripflow.travel.model.Travel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ITravelRepository {
    Travel getTravelInfoByTravelId(Long travelId);

    void saveTravelSchedule(Travel travel);
}
