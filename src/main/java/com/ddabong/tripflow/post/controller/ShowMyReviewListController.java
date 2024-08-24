package com.ddabong.tripflow.post.controller;

import com.amazonaws.services.s3.AmazonS3Client;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private String postImgRootPath = "postimg/";
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
                Long postId = item.getPostId();
                Long travelId = item.getTravelId();
                TravelDTO travelDTO = travelService.getTravelInfoByTravelId(travelId);

                List<Long> imageIds = postImageService.getImageIdByPostId(postId);
                List<String> url = new ArrayList<>();
                for(Long imageId : imageIds){
                    String curUrl = imageService.getImageUrlByImageId(imageId);
                    url.add(curUrl);
                }
                if(url.isEmpty()) {
                    url.add(String.valueOf(amazonS3Client.getUrl(bucket, postImgRootPath + "default/default_post_image.jpg")));
                }
                item.setUrl(url);
                item.setStartTime(travelDTO.getStartTime());
                item.setEndTime(travelDTO.getEndTime());

                long lDayAndNights = getDayAndNights(travelDTO.getStartTime(), travelDTO.getEndTime());
                long lDDay = getDDay(travelDTO.getStartTime());

                String dayAndNights = lDayAndNights + "박 " + (lDayAndNights + 1) + "일";
                String dDay = "D-" + lDDay;
                item.setDayAndNights(dayAndNights);
                item.setDDay(dDay);
            }

            reviewListResponseDTO.setMessage("Load My Review SUCCESS");
            reviewListResponseDTO.setStatus(200);
            reviewListResponseDTO.setData(reviewListDTOList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviewListResponseDTO;
    }

    private long getDDay(String startTime) throws ParseException {
        // 날짜 포맷 형식을 정의합니다.
        String dateFormatType = "yyyyMMdd";
        // SimpleDateFormat 객체를 생성하고 포맷 형식을 설정합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
        // 현재 날짜를 구합니다.
        Date now = new Date();
        // 현재 날짜를 지정된 포맷 형식에 맞게 변환합니다.
        String todayTime = simpleDateFormat.format(now);
        // 시작일과 종료일 문자열을 Date 객체로 변환합니다.
        Date from = simpleDateFormat.parse(startTime);
        Date today = simpleDateFormat.parse(todayTime);
        // 두 날짜의 차이를 밀리초 단위로 계산합니다.
        long diff = from.getTime() - today.getTime();
        // 밀리초 단위의 차이를 일수로 변환합니다.
        long dDay = diff / 86400000L;
        // 결과를 출력합니다.
        return dDay;
    }

    private long getDayAndNights(String startTime, String endTime) throws ParseException {
        // 날짜 포맷 형식을 정의합니다.
        String dateFormatType = "yyyyMMdd";
        // SimpleDateFormat 객체를 생성하고 포맷 형식을 설정합니다.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType);
        // 시작일과 종료일 문자열을 Date 객체로 변환합니다.
        Date from = simpleDateFormat.parse(startTime);
        Date to = simpleDateFormat.parse(endTime);
        // 두 날짜의 차이를 밀리초 단위로 계산합니다.
        long diff = to.getTime() - from.getTime();
        // 밀리초 단위의 차이를 일수로 변환합니다.
        long dayAndNights = diff / 86400000L;
        // 결과를 출력합니다.
        return dayAndNights;
    }

}
