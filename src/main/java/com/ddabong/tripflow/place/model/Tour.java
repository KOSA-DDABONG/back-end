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
public class Tour {
    @Id
    private Long placeId;
    private String placeDetail;
    private int single;
    private int couple;
    private int parents;
    private int family;
    private int friends;
    private int experience;
    private int park;
    private int uniqueTravel;
    private int nature;
    private int culture;
    private int festival;
    private int shopping;
    private int history;
    private int walking;
    private int city;
    private int etc;
    private int ocean;
    private int mountain;
}
