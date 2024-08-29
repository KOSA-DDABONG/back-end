package com.ddabong.tripflow.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TravelDTO {
    private Long travelId;
    private Long memberId;
    private Timestamp createdTime;
    private String startTime;
                                                                                    private String endTime;
    private Long chatLogId;
}
