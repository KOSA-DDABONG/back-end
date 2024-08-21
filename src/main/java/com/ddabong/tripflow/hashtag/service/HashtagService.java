package com.ddabong.tripflow.hashtag.service;

import com.ddabong.tripflow.hashtag.dao.IHashtagRepository;
import com.ddabong.tripflow.hashtag.model.Hashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HashtagService implements IHashtagService{
    @Autowired
    private IHashtagRepository iHashtagRepository;


    @Override
    public List<Long> isExistHashtag(List<String> hashtags) {
        Set<String> set = new HashSet<String>();
        List<Long> hashtagIdList = new ArrayList<>();

        for (String ht : hashtags){
            set.add(ht);
        }

        Iterator<String> iterSet = set.iterator();
        while(iterSet.hasNext()){
            String curHashtag = iterSet.next();
            int isExist = iHashtagRepository.isExistHashtag(curHashtag);

            if(isExist == 0){
                Hashtag hashtag = new Hashtag();
                hashtag.setHashtagName(curHashtag);
                iHashtagRepository.createNewHashtag(hashtag);
            }

            Long curHashtagId = iHashtagRepository.getHashtagIdByHashtagName(curHashtag);
            hashtagIdList.add(curHashtagId);
        }

        return hashtagIdList;
    }

    @Override
    public String getHashtagNameByHashtagId(Long hashtagId) {
        return iHashtagRepository.getHashtagNameByHashtagId(hashtagId);
    }
}
