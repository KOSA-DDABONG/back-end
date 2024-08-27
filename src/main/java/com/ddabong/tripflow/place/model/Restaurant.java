package com.ddabong.tripflow.place.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    private Long placeId;
    private String foodType;
    private int bar;
    private String parking;
    private int operatingHours0;
    private int operatingHours1;
    private int operatingHours2;
    private int operatingHours3;
    private int operatingHours4;
    private int operatingHours5;
}
