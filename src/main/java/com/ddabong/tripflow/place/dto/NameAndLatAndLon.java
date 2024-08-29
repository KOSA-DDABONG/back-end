package com.ddabong.tripflow.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NameAndLatAndLon {
    private String name;
    private Double latitude;
    private Double longitude;
}
