package com.ddabong.tripflow.travel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelId;

    private Long memberId;
    private Timestamp createdTime;
    private String startTime;
    private String endTime;
}
