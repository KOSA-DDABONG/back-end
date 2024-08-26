package com.ddabong.tripflow.travel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TravelPlaceJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPlaceJoinId;

    private Long placeId;
    private Long travelId;
    private int dayNum;
    private int sequence;
}
