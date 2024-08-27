package com.ddabong.tripflow.travel.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelPlaceJoinDTO {
    private Long placeId;
    private Long travelId;
    private int dayNum;
    private int sequence;
}
