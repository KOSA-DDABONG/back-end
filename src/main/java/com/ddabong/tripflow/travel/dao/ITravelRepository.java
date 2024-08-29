package com.ddabong.tripflow.travel.dao;

import com.ddabong.tripflow.travel.model.MergeTravelPlace;
import com.ddabong.tripflow.travel.model.Travel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITravelRepository {
    Travel getTravelInfoByTravelId(Long travelId);

    void saveTravelSchedule(Travel travel);

    List<Travel> loadPastTravelList(Long memberId);

    List<Travel> loadFutureTravelList(Long memberId);

    List<Travel> loadPresentTravelList(Long memberId);


    List<MergeTravelPlace> searchMyTravel(@Param("memberId") Long memberId, @Param("travelId")Long travelId);
}
