package com.ddabong.tripflow.image.model;

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
public class PostImage {
    @Id
    private Long imageId;

    private Long postId;
    private Long travelId;
}
