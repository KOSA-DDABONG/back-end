package com.ddabong.tripflow.travel.dto;

import com.ddabong.tripflow.comment.dto.CommentInfoDTO;
import com.ddabong.tripflow.place.dto.LatAndLon;
import com.ddabong.tripflow.place.dto.NameAndLatAndLon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadDetailTravelScheduleDTO {
    private Long memberId;
    private Long travelId;

    private List<NameAndLatAndLon> tour; // 관광지 위경도
    private List<NameAndLatAndLon> restaurant; //식당 위경도
    private List<NameAndLatAndLon> hotel; // 숙박 위경도
}
