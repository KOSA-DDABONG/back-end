package com.ddabong.tripflow.place.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    private String placeName;
    private Double latitude;
    private Double longitude;

    private String tempAddress;
    private int placeType;
    private String sidoName;
    private String siggName;
    private String mpmdName;
    private String liName;
    private String bunjiName;
    private String streetName;
    private String streetNameDetail;
    private int teenager;
    private int twenties;
    private int thirties;
    private int fourties;
    private int fifties;
    private int sixties;
}
