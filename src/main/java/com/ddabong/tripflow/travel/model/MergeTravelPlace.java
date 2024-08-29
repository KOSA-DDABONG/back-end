package com.ddabong.tripflow.travel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MergeTravelPlace {
    private Long travelId;
    private Long memberId;
    private Timestamp createdTime;
    private String startTime;
    private String endTime;
    private Long chatLogId;

    @Id
    private Long travelPlaceJoinId;
    private Long placeId;
    private int dayNum;
    private int sequence;
}
