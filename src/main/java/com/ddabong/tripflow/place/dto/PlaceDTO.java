package com.ddabong.tripflow.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceDTO {
    private Long placeId;
    private int placeType;
    private String placeName;
    private Double latitude;
    private Double longitude;
}
