package com.ddabong.tripflow.hashtag.service;

import java.util.List;

public interface IHashtagService {
    List<Long> isExistHashtag(List<String> hashtags);
}
