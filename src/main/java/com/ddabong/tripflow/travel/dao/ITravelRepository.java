package com.ddabong.tripflow.travel.dao;

import com.ddabong.tripflow.travel.model.Travel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ITravelRepository {
    Travel getTravelInfoByTravelId(Long travelId);

    void saveTravelSchedule(Travel travel);

    List<Travel> loadPastTravelList(Long memberId);
}
