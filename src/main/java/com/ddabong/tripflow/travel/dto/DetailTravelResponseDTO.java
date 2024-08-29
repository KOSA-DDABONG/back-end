package com.ddabong.tripflow.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailTravelResponseDTO {
    private String message;
    private int status;
    private List<LoadDetailTravelScheduleDTO> data;
}
