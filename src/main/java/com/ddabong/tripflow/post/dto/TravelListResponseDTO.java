package com.ddabong.tripflow.post.dto;

import com.ddabong.tripflow.travel.dto.LoadTravelScheduleListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelListResponseDTO {
    private String message;
    private int status;
    private List<LoadTravelScheduleListDTO> data;
}
