package com.ddabong.tripflow.travel.controller;

import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.ddabong.tripflow.place.dto.NameAndLatAndLon;
import com.ddabong.tripflow.place.dto.PlaceDTO;
import com.ddabong.tripflow.place.service.IPlaceService;
import com.ddabong.tripflow.travel.dto.DetailTravelResponseDTO;
import com.ddabong.tripflow.travel.dto.LoadDetailTravelScheduleDTO;
import com.ddabong.tripflow.travel.model.MergeTravelPlace;
import com.ddabong.tripflow.travel.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/myinfo")
public class LoadMyDetailTravelScheduleController {
    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IPlaceService placeService;

    @GetMapping("/travel/{id}")
    public DetailTravelResponseDTO loadDetailTravel(@PathVariable("id") Long travelId){
        DetailTravelResponseDTO detailTravelResponseDTO = new DetailTravelResponseDTO("Load Detail Travel Fail", 500, null);

        try {
            Long memberId = memberService.getMemberIdByUserId(getMemberInfoService.getUserIdByJWT());

            List<MergeTravelPlace> mergeTravelPlaces = travelService.searchMyTravel(memberId, travelId);

            List<LoadDetailTravelScheduleDTO> loadDetailTravelScheduleDTOs = new ArrayList<>();
            int dayNum = 0;
            for (MergeTravelPlace tp : mergeTravelPlaces){
                LoadDetailTravelScheduleDTO loadDetailTravelScheduleDTO = new LoadDetailTravelScheduleDTO();

                loadDetailTravelScheduleDTO.setTravelId(tp.getTravelId());
                loadDetailTravelScheduleDTO.setMemberId(tp.getMemberId());
                loadDetailTravelScheduleDTO.setDayNum(tp.getDayNum());

                if(dayNum == tp.getDayNum()) { continue; }
                loadDetailTravelScheduleDTOs.add(loadDetailTravelScheduleDTO);
                dayNum = tp.getDayNum();
            }

            for(LoadDetailTravelScheduleDTO ldts : loadDetailTravelScheduleDTOs) {
                List<NameAndLatAndLon> tourList = new ArrayList<>();
                List<NameAndLatAndLon> hotelList = new ArrayList<>();
                List<NameAndLatAndLon> restaurantList = new ArrayList<>();
                List<NameAndLatAndLon> placeList = new ArrayList<>();

                int foodSeq = 0;
                for (MergeTravelPlace tp : mergeTravelPlaces) {
                    NameAndLatAndLon nll = new NameAndLatAndLon("", 0.0, 0.0, "");

                    PlaceDTO placeDTO = placeService.getPlaceInfoByPlaceId(tp.getPlaceId());
                    nll.setName(placeDTO.getPlaceName());
                    nll.setLatitude(placeDTO.getLatitude());
                    nll.setLongitude(placeDTO.getLongitude());
                    nll.setPlaceType("");

                    if (placeDTO.getPlaceType() == 0 && tp.getDayNum() == ldts.getDayNum()) {
                        tourList.add(nll);
                    } else if (placeDTO.getPlaceType() == 1 && tp.getDayNum() == ldts.getDayNum()) {
                        hotelList.add(nll);
                    } else if (placeDTO.getPlaceType() == 2 && tp.getDayNum() == ldts.getDayNum()) {
                        if(foodSeq == 0){nll.setPlaceType("아침");}
                        else if(foodSeq == 1){nll.setPlaceType("점심");}
                        else if(foodSeq == 2){nll.setPlaceType("저녁");}
                        foodSeq += 1;
                        restaurantList.add(nll);
                    }
                }

                for(NameAndLatAndLon p : tourList){
                    placeList.add(p);
                }
                for(NameAndLatAndLon p : restaurantList){
                    placeList.add(p);
                }
                // 아침, 점심, 저녁 장소를 순서대로 유지하면서 나머지 장소를 거리순으로 삽입
                List<NameAndLatAndLon> sortedPlaces = sortPlaces(placeList);

                for(NameAndLatAndLon p : hotelList){
                    sortedPlaces.add(p);
                }

                ldts.setPlace(sortedPlaces);
            }



            detailTravelResponseDTO.setData(loadDetailTravelScheduleDTOs);
            detailTravelResponseDTO.setStatus(200);
            detailTravelResponseDTO.setMessage("Load Complete");

        } catch (Exception e){
            e.printStackTrace();
        }

        return detailTravelResponseDTO;
    }

    // 장소들을 정렬하는 메소드
    public static List<NameAndLatAndLon> sortPlaces(List<NameAndLatAndLon> places) {
        // 아침, 점심, 저녁 식사 장소를 먼저 분리
        NameAndLatAndLon breakfast = null, lunch = null, dinner = null;
        List<NameAndLatAndLon> others = new ArrayList<>();

        for (NameAndLatAndLon place : places) {
            if (place.getPlaceType().equals("아침")) {
                breakfast = place;
            } else if (place.getPlaceType().equals("점심")) {
                lunch = place;
            } else if (place.getPlaceType().equals("저녁")) {
                dinner = place;
            } else {
                others.add(place);
            }
        }

        // 기타 장소들을 아침, 점심, 저녁 순서에 맞게 거리 기준으로 정렬
        List<NameAndLatAndLon> sortedPlaces = new ArrayList<>();
        sortedPlaces.add(breakfast);
        sortedPlaces.addAll(getNearestPlaces(breakfast, others, lunch));
        sortedPlaces.add(lunch);
        sortedPlaces.addAll(getNearestPlaces(lunch, others, dinner));
        sortedPlaces.add(dinner);
        sortedPlaces.addAll(getNearestPlaces(dinner, others, null));

        return sortedPlaces;
    }

    // 인접한 장소를 거리 순서대로 정렬하는 메소드
    private static List<NameAndLatAndLon> getNearestPlaces(NameAndLatAndLon start, List<NameAndLatAndLon> others, NameAndLatAndLon end) {
        List<NameAndLatAndLon> sorted = new ArrayList<>();
        NameAndLatAndLon current = start;

        while (!others.isEmpty()) {
            NameAndLatAndLon nearest = null;
            Double nearestDistance = Double.MAX_VALUE;

            for (NameAndLatAndLon place : others) {
                Double distance = current.distanceTo(place);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = place;
                }
            }

            if (nearest != null) {
                sorted.add(nearest);
                others.remove(nearest);
                current = nearest;
            }
        }

        return sorted;
    }

}
