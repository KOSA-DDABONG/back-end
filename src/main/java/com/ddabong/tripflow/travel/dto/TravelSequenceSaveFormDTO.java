package com.ddabong.tripflow.travel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelSequenceSaveFormDTO {
    private Long memberId;
    private String startTime;
    private String endTime;
    private Long chatlogid;

    private Long placeId;
    private Long travelId;
    private int dayNum;
    private int sequence;
}
