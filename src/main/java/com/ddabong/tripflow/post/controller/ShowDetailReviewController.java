package com.ddabong.tripflow.post.controller;


import com.ddabong.tripflow.comment.dto.CommentDTO;
import com.ddabong.tripflow.comment.dto.CommentInfoDTO;
import com.ddabong.tripflow.comment.service.ICommentService;
import com.ddabong.tripflow.ddabong.service.IDdabongService;
import com.ddabong.tripflow.hashtag.service.IHashtagJoinService;
import com.ddabong.tripflow.hashtag.service.IHashtagService;
import com.ddabong.tripflow.image.service.IImageService;
import com.ddabong.tripflow.image.service.IPostImageService;
import com.ddabong.tripflow.image.service.IProfileImageService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.ddabong.tripflow.place.dto.LatAndLon;
import com.ddabong.tripflow.post.dto.DetailReviewInfoDTO;
import com.ddabong.tripflow.post.dto.DetailReviewResponseDTO;
import com.ddabong.tripflow.post.service.IPostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/post")
public class ShowDetailReviewController {

    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private IPostImageService postImageService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IHashtagJoinService hashtagJoinService;
    @Autowired
    private IHashtagService hashtagService;
    @Autowired
    private IDdabongService ddabongService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IProfileImageService profileImageService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;
    private String postImgRootPath = "postimg/";
    private String profileRootPath = "profile/";

    @CrossOrigin
    @Transactional
    @GetMapping("/{id}")
    public DetailReviewResponseDTO showDetailReview(@PathVariable("id") Long postId){
        DetailReviewResponseDTO detailReviewResponseDTO = new DetailReviewResponseDTO("Load Detail Review FAIL", 500, null);
        DetailReviewInfoDTO detailReviewInfoDTO = new DetailReviewInfoDTO(0L, postId, 0L,
                null,null,null,null,
                "닉네임이 없습니다.", "내용이 없습니다.", null, 0, 0,
                false, null);

        System.out.println("해당 게시글 조회 중...");
        try{
            System.out.println("게시글 사진 불러오는 중.");
            List<String> postImageURLs = loadPostImage(postId);
            detailReviewInfoDTO.setUrl(postImageURLs);


            Long travelId = postService.getTravelIdByPostId(postId);
            detailReviewInfoDTO.setTravelId(travelId);

            System.out.println("게시글 관광지 위도 경도 불러오는 중.");
            List<LatAndLon> tours = getTourLatAndLon(travelId);
            detailReviewInfoDTO.setTour(tours);

            System.out.println("게시글 식당 위도 경도 불러오는 중.");
            List<LatAndLon> restaurants = getRestaurantLatAndLon(travelId);
            detailReviewInfoDTO.setRestaurant(restaurants);

            System.out.println("게시글 숙박 위도 경도 불러오는 중.");
            List<LatAndLon> hotels = getHotelLatAndLon(travelId);
            detailReviewInfoDTO.setHotel(hotels);
            
            System.out.println("게시글 작성자 불러오는 중.");
            Long writerMemberId = postService.getMemberIdByPostId(postId);
            String nickName = memberService.getNicknameByMemberId(writerMemberId);
            detailReviewInfoDTO.setMemberId(writerMemberId);
            detailReviewInfoDTO.setNickName(nickName);

            System.out.println("게시글 내용 불러오는 중.");
            String content = postService.getContentByPostId(postId);
            detailReviewInfoDTO.setContent(content);

            System.out.println("게시글 해시태그 불러오는 중.");
            List<String> hashtags = getHashtagList(postId);
            detailReviewInfoDTO.setHashtags(hashtags);

            System.out.println("게시글 좋아요수 불러오는 중.");
            int likeCnt = ddabongService.getCountLikeNumByPostId(postId);
            detailReviewInfoDTO.setLikeCnt(likeCnt);

            System.out.println("게시글 좋아요 클릭 여부 확인 중");
            Long loginUserMemberId = memberService.getMemberIdByUserId(getMemberInfoService.getUserIdByJWT());
            Map<String, Long> params = new HashMap<>();
            params.put("memberId", loginUserMemberId);
            params.put("postId", postId);
            Boolean isLike = ddabongService.checkIsExistByMemberIdAndPostId(params);
            if(isLike) {
                detailReviewInfoDTO.setIsLike(true);
            } else if (!isLike) {
                detailReviewInfoDTO.setIsLike(false);
            }

            System.out.println("게시글 전체 댓글 수 불러오는 중.");
            int commentCnt = commentService.getCountCommentNumByPostId(postId);
            detailReviewInfoDTO.setCommentCnt(commentCnt);
            System.out.println("게시글 댓글 정보 불러오는 중.");
            List<CommentInfoDTO> commentInfoDTOs = getCommentInfo(postId);
            detailReviewInfoDTO.setCommentInfoDTOs(commentInfoDTOs);

            detailReviewResponseDTO.setMessage("Show Detail Review SUCCESS");
            detailReviewResponseDTO.setStatus(200);
            detailReviewResponseDTO.setData(detailReviewInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailReviewResponseDTO;
    }

    private List<CommentInfoDTO> getCommentInfo(Long postId) {
        List<CommentInfoDTO> commentInfoDTOs = new ArrayList<>();
        List<Long> commentIDs = commentService.getCommentIDsByPostId(postId);

        for (Long commentId : commentIDs){
            CommentInfoDTO commentInfoDTO = new CommentInfoDTO();
            Long memberId = commentService.getMemberIdByCommentId(commentId);

            String profileImageURL = "https://" + bucket + ".s3." + region + ".amazonaws.com/"
                    + profileRootPath + "default/default_profile_image.png";
            int isExist = profileImageService.isExistProfileUrl(memberId);
            if(isExist > 0){
                Long imageId = profileImageService.getImageIdByMemberId(memberId);
                profileImageURL = imageService.getProfileUrlByImageId(imageId);
            }
            commentInfoDTO.setProfileUrl(profileImageURL);

            String nickname = memberService.getNicknameByMemberId(memberId);
            commentInfoDTO.setNickName(nickname);

            commentInfoDTO.setContent(commentService.getCommentContentByCommentId(commentId));

            commentInfoDTOs.add(commentInfoDTO);
        }

        return commentInfoDTOs;
    }

    private List<String> getHashtagList(Long postId) {
        List<String> hashtags = new ArrayList<>();

        List<Long> hashtagIDs = hashtagJoinService.getHashtagIDsByPostId(postId);
        for(Long hashtagId : hashtagIDs){
            hashtags.add(hashtagService.getHashtagNameByHashtagId(hashtagId));
        }

        return hashtags;
    }

    private List<LatAndLon> getHotelLatAndLon(Long travelId) {
        List<LatAndLon> latAndLons = new ArrayList<>();

        LatAndLon hotel1 = new LatAndLon(35.09919408, 128.92803);
        LatAndLon hotel2 = new LatAndLon(35.10112933, 128.9285519);
        LatAndLon hotel3 = new LatAndLon(35.22680247, 129.2213595);

        latAndLons.add(hotel1);
        latAndLons.add(hotel2);
        latAndLons.add(hotel3);

        return latAndLons;
    }

    private List<LatAndLon> getRestaurantLatAndLon(Long travelId) {
        List<LatAndLon> latAndLons = new ArrayList<>();

        LatAndLon res1 = new LatAndLon(35.0992082002786, 128.957156748878);
        LatAndLon res2 = new LatAndLon(35.0984680803714, 128.993059970547);
        LatAndLon res3 = new LatAndLon(35.0913983306379, 129.0431725082);

        LatAndLon res4 = new LatAndLon(35.100271262114, 129.030398737648);
        LatAndLon res5 = new LatAndLon(35.1026918330501, 129.035119317833);
        LatAndLon res6 = new LatAndLon(35.1024676015828, 129.028312505901);

        LatAndLon res7 = new LatAndLon(35.158568574253, 129.171743752647);
        LatAndLon res8 = new LatAndLon(35.1557193179273, 129.122639564934);
        LatAndLon res9 = new LatAndLon(35.1538649711286, 129.11319157088);

        LatAndLon res10 = new LatAndLon(35.163583461941, 129.118768427278);
        LatAndLon res11 = new LatAndLon(35.1638121645859, 129.11674587996);
        LatAndLon res12 = new LatAndLon(35.1698614848869, 129.128290064333);

        latAndLons.add(res1); // 감천 문화 마을
        latAndLons.add(res2); // 아미동 비석문화마을
        latAndLons.add(res3); //영도다리

        latAndLons.add(res4); // 초량이바구길
        latAndLons.add(res5); // 자갈치 시장
        latAndLons.add(res6); // 용두산공원

        latAndLons.add(res7); // 해운대시장
        latAndLons.add(res8); // 동백해안 산책로
        latAndLons.add(res9); // 광안리해수욕장

        latAndLons.add(res10); // 민락수변공원
        latAndLons.add(res11); // 신세계백화점 센텀시티점
        latAndLons.add(res12); // 벡스코

        return latAndLons;
    }

    private List<LatAndLon> getTourLatAndLon(Long travelId) {
        List<LatAndLon> latAndLons = new ArrayList<>();

        LatAndLon tour1 = new LatAndLon(35.09731, 129.01028); // 감천 문화 마을
        LatAndLon tour2 = new LatAndLon(35.09906, 129.01271); // 아미동 비석문화마을
        LatAndLon tour3 = new LatAndLon(35.095295, 129.03656); //영도다리

        LatAndLon tour4 = new LatAndLon(35.11635, 129.03874); // 초량이바구길
        LatAndLon tour5 = new LatAndLon(35.096664, 129.03055); // 자갈치 시장
        LatAndLon tour6 = new LatAndLon(35.10072, 129.03264); // 용두산공원

        LatAndLon tour7 = new LatAndLon(35.16165, 129.16211); // 해운대시장
        LatAndLon tour8 = new LatAndLon(35.15196, 129.15263); // 동백해안 산책로
        LatAndLon tour9 = new LatAndLon(35.15318, 129.11887); // 광안리해수욕장

        LatAndLon tour10 = new LatAndLon(35.15591, 129.13435); // 민락수변공원
        LatAndLon tour11 = new LatAndLon(35.168625, 129.12907); // 신세계백화점 센텀시티점
        LatAndLon tour12 = new LatAndLon(35.169247, 129.13628); // 벡스코

        latAndLons.add(tour1); // 감천 문화 마을
        latAndLons.add(tour2); // 아미동 비석문화마을
        latAndLons.add(tour3); //영도다리

        latAndLons.add(tour4); // 초량이바구길
        latAndLons.add(tour5); // 자갈치 시장
        latAndLons.add(tour6); // 용두산공원

        latAndLons.add(tour7); // 해운대시장
        latAndLons.add(tour8); // 동백해안 산책로
        latAndLons.add(tour9); // 광안리해수욕장

        latAndLons.add(tour10); // 민락수변공원
        latAndLons.add(tour11); // 신세계백화점 센텀시티점
        latAndLons.add(tour12); // 벡스코

        return latAndLons;
    }

    private List<String> loadPostImage(Long postId) {
        List<Long> postImageIDs = postImageService.getImageIdByPostId(postId);
        List<String> url = new ArrayList<>();

        for(Long imageId : postImageIDs){
            url.add(imageService.getImageUrlByImageId(imageId));
        }
        if(url.isEmpty()) {
            url.add("https://" + bucket + ".s3." + region + ".amazonaws.com/"
                    + postImgRootPath + "default/default_post_image.jpg");
        }

        return url;
    }
}
