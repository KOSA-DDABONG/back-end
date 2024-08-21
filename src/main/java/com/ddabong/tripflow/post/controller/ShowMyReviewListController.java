package com.ddabong.tripflow.post.controller;

import com.ddabong.tripflow.image.service.IImageService;
import com.ddabong.tripflow.image.service.IPostImageService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.ddabong.tripflow.post.dto.ReviewListDTO;
import com.ddabong.tripflow.post.dto.ReviewListResponseDTO;
import com.ddabong.tripflow.post.service.IPostService;
import com.ddabong.tripflow.travel.dto.TravelDTO;
import com.ddabong.tripflow.travel.service.ITravelService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/myinfo")
public class ShowMyReviewListController {
    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IPostImageService postImageService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private ITravelService travelService;
    @Transactional
    @GetMapping("/review/list")
    public ReviewListResponseDTO showMyReviewList(){
        ReviewListResponseDTO reviewListResponseDTO = new ReviewListResponseDTO("Load My Review FAIL", 500, null);

        try {
            System.out.println("나의 후기 목록 불러오는 중...");
            String userId = getMemberInfoService.getUserIdByJWT();
            Long memberId = memberService.getMemberIdByUserId(userId);
            List<ReviewListDTO> reviewListDTOList = postService.getMyReview(memberId);

            for(ReviewListDTO item : reviewListDTOList){
                Long travelId = item.getTravelId();
                TravelDTO travelDTO = travelService.getTravelInfoByTravelId(travelId);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviewListResponseDTO;
    }
}
