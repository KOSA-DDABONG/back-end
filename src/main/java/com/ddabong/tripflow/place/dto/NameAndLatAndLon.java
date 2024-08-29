package com.ddabong.tripflow.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameAndLatAndLon {
    private String name;
    private Double latitude;
    private Double longitude;
    private String placeType;

    public NameAndLatAndLon(String name, Double latitude, Double longitude, String placeType) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeType = placeType;
    }
    // 두 장소 간의 거리 계산 (Haversine formula)
    public Double distanceTo(NameAndLatAndLon other) {
        Double dLat = Math.toRadians(other.latitude - this.latitude);
        Double dLon = Math.toRadians(other.longitude - this.longitude);
        Double lat1 = Math.toRadians(this.latitude);
        Double lat2 = Math.toRadians(other.latitude);

        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double earthRadius = 6371.0; // 지구 반경 (킬로미터)
        return earthRadius * c;
    }
}
