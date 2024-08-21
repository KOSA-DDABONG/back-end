package com.ddabong.tripflow.hashtag.model;

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
public class HashtagJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagJoinId;

    private Long postId;
    private Long travelId;
    private Long hashtagId;
}
