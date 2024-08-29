package com.ddabong.tripflow.travel.controller;

import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.ddabong.tripflow.post.dto.TravelListResponseDTO;
import com.ddabong.tripflow.travel.dto.LoadTravelScheduleListDTO;
import com.ddabong.tripflow.travel.dto.TravelDTO;
import com.ddabong.tripflow.travel.service.ITravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/myinfo")
public class LoadMyTravelSchedule {
    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private ITravelService travelService;

    @GetMapping("/past/list")
    public TravelListResponseDTO loadPastTravelList(){
        TravelListResponseDTO travelListResponseDTO = new TravelListResponseDTO("No Data", 500, null);

        try {
            String userId = getMemberInfoService.getUserIdByJWT();
            Long memberId = memberService.getMemberIdByUserId(userId);
            List<TravelDTO> travelDTOs = travelService.loadPastTravelList(memberId);
            List<LoadTravelScheduleListDTO> loadTravelScheduleListDTOs = new ArrayList<>();

            // 오늘 날짜 생성 (yyyyMMdd 포맷)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            String todayStr = dateFormat.format(today);

            for (TravelDTO travelDTO : travelDTOs){
                LoadTravelScheduleListDTO item = new LoadTravelScheduleListDTO();
                item.setTravelId(travelDTO.getTravelId());
                item.setStartTime(travelDTO.getStartTime());
                item.setEndTime(travelDTO.getEndTime());

                if(isPast(travelDTO.getEndTime()) == false){
                    continue;
                }

                long lDayAndNights = getDayAndNights(travelDTO.getStartTime(), travelDTO.getEndTime());
                long lDDay = getDDay(travelDTO.getStartTime());

                String dayAndNights = lDayAndNights + "박 " + (lDayAndNights + 1) + "일";
                String dDay = "D-" + lDDay;
                item.setDayAndNights(dayAndNights);
                item.setDDay(dDay);

                System.out.println("travel id : " + item.getTravelId());
                System.out.println("start time : " + item.getStartTime());
                System.out.println("end time : " + item.getEndTime());
                System.out.println("낮 밤 : " + item.getDayAndNights());
                System.out.println("디데이 : " + item.getDDay());
                System.out.println("==========");

                loadTravelScheduleListDTOs.add(item);
            }

            travelListResponseDTO.setData(loadTravelScheduleListDTOs);
            travelListResponseDTO.setStatus(200);
            travelListResponseDTO.setMessage("Load Complete Past Schedule");

        } catch (Exception e){
            e.printStackTrace();
        }

        return travelListResponseDTO;
    }

    @GetMapping("/future/list")
    public TravelListResponseDTO loadFutureTravelList(){
        TravelListResponseDTO travelListResponseDTO = new TravelListResponseDTO("No Data", 500, null);

        try {
            String userId = getMemberInfoService.getUserIdByJWT();
            Long memberId = memberService.getMemberIdByUserId(userId);
            List<TravelDTO> travelDTOs = travelService.loadFutureTravelList(memberId);
            List<LoadTravelScheduleListDTO> loadTravelScheduleListDTOs = new ArrayList<>();

            // 오늘 날짜 생성 (yyyyMMdd 포맷)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            String todayStr = dateFormat.format(today);

            for (TravelDTO travelDTO : travelDTOs){
                LoadTravelScheduleListDTO item = new LoadTravelScheduleListDTO();
                item.setTravelId(travelDTO.getTravelId());
                item.setStartTime(travelDTO.getStartTime());
                item.setEndTime(travelDTO.getEndTime());

                if(isFuture(travelDTO.getStartTime()) == false){
                    continue;
                }

                long lDayAndNights = getDayAndNights(travelDTO.getStartTime(), travelDTO.getEndTime());
                long lDDay = getDDay(travelDTO.getStartTime());

                String dayAndNights = lDayAndNights + "박 " + (lDayAndNights + 1) + "일";
                String dDay = "D-" + lDDay;
                item.setDayAndNights(dayAndNights);
                item.setDDay(dDay);

                System.out.println("travel id : " + item.getTravelId());
                System.out.println("start time : " + item.getStartTime());
                System.out.println("end time : " + item.getEndTime());
                System.out.println("낮 밤 : " + item.getDayAndNights());
                System.out.println("디데이 : " + item.getDDay());
                System.out.println("==========");

                loadTravelScheduleListDTOs.add(item);
            }

            travelListResponseDTO.setData(loadTravelScheduleListDTOs);
            travelListResponseDTO.setStatus(200);
            travelListResponseDTO.setMessage("Load Complete Past Schedule");

        } catch (Exception e){
            e.printStackTrace();
        }

        return travelListResponseDTO;
    }

    @GetMapping("/present/list")
    public TravelListResponseDTO loadPresentTravelList(){
        TravelListResponseDTO travelListResponseDTO = new TravelListResponseDTO("No Data", 500, null);

        try {
            String userId = getMemberInfoService.getUserIdByJWT();
            Long memberId = memberService.getMemberIdByUserId(userId);
            List<TravelDTO> travelDTOs = travelService.loadPresentTravelList(memberId);
            List<LoadTravelScheduleListDTO> loadTravelScheduleListDTOs = new ArrayList<>();

            // 오늘 날짜 생성 (yyyyMMdd 포맷)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            String todayStr = dateFormat.format(today);

            for (TravelDTO travelDTO : travelDTOs){
                LoadTravelScheduleListDTO item = new LoadTravelScheduleListDTO();
                item.setTravelId(travelDTO.getTravelId());
                item.setStartTime(travelDTO.getStartTime());
                item.setEndTime(travelDTO.getEndTime());

                if(isPresent(travelDTO.getStartTime(), travelDTO.getEndTime()) == false){
                    continue;
                }

                long lDayAndNights = getDayAndNights(travelDTO.getStartTime(), travelDTO.getEndTime());
                long lDDay = getDDay(travelDTO.getStartTime());

                String dayAndNights = lDayAndNights + "박 " + (lDayAndNights + 1) + "일";
                String dDay = "D-" + lDDay;
                item.setDayAndNights(dayAndNights);
                item.setDDay(dDay);

                System.out.println("travel id : " + item.getTravelId());
                System.out.println("start time : " + item.getStartTime());
                System.out.println("end time : " + item.getEndTime());
                System.out.println("낮 밤 : " + item.getDayAndNights());
                System.out.println("디데이 : " + item.getDDay());
                System.out.println("==========");

                loadTravelScheduleListDTOs.add(item);
            }

            travelListResponseDTO.setData(loadTravelScheduleListDTOs);
            travelListResponseDTO.setStatus(200);
            travelListResponseDTO.setMessage("Load Complete Past Schedule");

        } catch (Exception e){
            e.printStackTrace();
        }

        return travelListResponseDTO;
    }

    private boolean isPresent(String startTime, String endTime) throws ParseException {
        // 날짜 포맷 형식을 정의합니다.
        String dateFormatType = "yyyyMMdd";
        // SimpleDateFormat 객체를 생성하고 포맷 형식을 설정합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
        // 현재 날짜를 구합니다.
        Date now = new Date();
        // 현재 날짜를 지정된 포맷 형식에 맞게 변환합니다.
        String todayTime = simpleDateFormat.format(now);
        // 시작일과 종료일 문자열을 Date 객체로 변환합니다.
        Date from = simpleDateFormat.parse(startTime);
        Date to = simpleDateFormat.parse(endTime);
        Date today = simpleDateFormat.parse(todayTime);
        // 두 날짜의 차이를 밀리초 단위로 계산합니다.
        long pDiff = from.getTime() - today.getTime();
        long nDiff = to.getTime() - today.getTime();
        // 밀리초 단위의 차이를 일수로 변환합니다.
        long pdDay = pDiff / 86400000L;
        long ndDay = nDiff / 86400000L;

        if(pdDay <= 0 && ndDay >= 0) {return true;}
        return false;
    }

    private boolean isFuture(String startTime) throws ParseException {
        // 날짜 포맷 형식을 정의합니다.
        String dateFormatType = "yyyyMMdd";
        // SimpleDateFormat 객체를 생성하고 포맷 형식을 설정합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
        // 현재 날짜를 구합니다.
        Date now = new Date();
        // 현재 날짜를 지정된 포맷 형식에 맞게 변환합니다.
        String todayTime = simpleDateFormat.format(now);
        // 시작일과 종료일 문자열을 Date 객체로 변환합니다.
        Date from = simpleDateFormat.parse(startTime);
        Date today = simpleDateFormat.parse(todayTime);
        // 두 날짜의 차이를 밀리초 단위로 계산합니다.
        long diff = from.getTime() - today.getTime();
        // 밀리초 단위의 차이를 일수로 변환합니다.
        long dDay = diff / 86400000L;

        if(dDay > 0) {return true;}
        return false;
    }

    private boolean isPast(String endTime) throws ParseException {
        // 날짜 포맷 형식을 정의합니다.
        String dateFormatType = "yyyyMMdd";
        // SimpleDateFormat 객체를 생성하고 포맷 형식을 설정합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
        // 현재 날짜를 구합니다.
        Date now = new Date();
        // 현재 날짜를 지정된 포맷 형식에 맞게 변환합니다.
        String todayTime = simpleDateFormat.format(now);
        // 시작일과 종료일 문자열을 Date 객체로 변환합니다.
        Date from = simpleDateFormat.parse(endTime);
        Date today = simpleDateFormat.parse(todayTime);
        // 두 날짜의 차이를 밀리초 단위로 계산합니다.
        long diff = from.getTime() - today.getTime();
        // 밀리초 단위의 차이를 일수로 변환합니다.
        long dDay = diff / 86400000L;

        if(dDay < 0) {return true;}
        return false;
    }

    private long getDDay(String startTime) throws ParseException {
        // 날짜 포맷 형식을 정의합니다.
        String dateFormatType = "yyyyMMdd";
        // SimpleDateFormat 객체를 생성하고 포맷 형식을 설정합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
        // 현재 날짜를 구합니다.
        Date now = new Date();
        // 현재 날짜를 지정된 포맷 형식에 맞게 변환합니다.
        String todayTime = simpleDateFormat.format(now);
        // 시작일과 종료일 문자열을 Date 객체로 변환합니다.
        Date from = simpleDateFormat.parse(startTime);
        Date today = simpleDateFormat.parse(todayTime);
        // 두 날짜의 차이를 밀리초 단위로 계산합니다.
        long diff = from.getTime() - today.getTime();
        // 밀리초 단위의 차이를 일수로 변환합니다.
        long dDay = diff / 86400000L;
        // 결과를 출력합니다.
        return dDay;
    }

    private long getDayAndNights(String startTime, String endTime) throws ParseException {
        // 날짜 포맷 형식을 정의합니다.
        String dateFormatType = "yyyyMMdd";
        // SimpleDateFormat 객체를 생성하고 포맷 형식을 설정합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
        // 시작일과 종료일 문자열을 Date 객체로 변환합니다.
        Date from = simpleDateFormat.parse(startTime);
        Date to = simpleDateFormat.parse(endTime);
        // 두 날짜의 차이를 밀리초 단위로 계산합니다.
        long diff = to.getTime() - from.getTime();
        // 밀리초 단위의 차이를 일수로 변환합니다.
        long dayAndNights = diff / 86400000L;
        // 결과를 출력합니다.
        return dayAndNights;
    }
}
