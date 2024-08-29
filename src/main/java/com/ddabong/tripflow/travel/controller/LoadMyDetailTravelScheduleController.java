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

                if(dayNum == tp.getDayNum()) { continue; }
                loadDetailTravelScheduleDTOs.add(loadDetailTravelScheduleDTO);
                int daynum = tp.getDayNum();
            }

            for(LoadDetailTravelScheduleDTO ldts : loadDetailTravelScheduleDTOs){
                List<NameAndLatAndLon> tour = new ArrayList<>();
                List<NameAndLatAndLon> hotel = new ArrayList<>();
                List<NameAndLatAndLon> restaurant = new ArrayList<>();
                for(MergeTravelPlace tp : mergeTravelPlaces){
                    NameAndLatAndLon nll = new NameAndLatAndLon("", 0.0, 0.0);

                    PlaceDTO placeDTO = placeService.getPlaceInfoByPlaceId(tp.getPlaceId());
                    nll.setName(placeDTO.getPlaceName());
                    nll.setLatitude(placeDTO.getLatitude());
                    nll.setLongitude(placeDTO.getLongitude());


                    if(placeDTO.getPlaceType() == 0){
                        tour.add(nll);
                    }
                    else if(placeDTO.getPlaceType() == 1){
                        hotel.add(nll);
                    }
                    else if(placeDTO.getPlaceType() == 2){
                        restaurant.add(nll);
                    }
                }

                ldts.setTour(tour);
                ldts.setHotel(hotel);
                ldts.setRestaurant(restaurant);
            }

            detailTravelResponseDTO.setData(loadDetailTravelScheduleDTOs);
            detailTravelResponseDTO.setStatus(200);
            detailTravelResponseDTO.setMessage("Load Complete");

        } catch (Exception e){
            e.printStackTrace();
        }

        return detailTravelResponseDTO;
    }
}
