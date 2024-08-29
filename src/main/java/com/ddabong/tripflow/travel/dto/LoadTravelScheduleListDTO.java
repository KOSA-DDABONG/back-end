package com.ddabong.tripflow.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadTravelScheduleListDTO {
    private Long travelId;
    private String startTime;
    private String endTime;
    private String dayAndNights;
    private String dDay;
}
