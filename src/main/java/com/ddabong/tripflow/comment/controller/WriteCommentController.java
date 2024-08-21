package com.ddabong.tripflow.comment.controller;

import com.ddabong.tripflow.comment.dto.CommentDTO;
import com.ddabong.tripflow.comment.dto.CommentInfoDTO;
import com.ddabong.tripflow.comment.dto.WriteCommentResponseDTO;
import com.ddabong.tripflow.comment.service.ICommentService;
import com.ddabong.tripflow.image.service.IImageService;
import com.ddabong.tripflow.image.service.IProfileImageService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
import com.ddabong.tripflow.post.service.IPostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/post")
public class WriteCommentController {
    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IProfileImageService profileImageService;
    @Autowired
    private IImageService imageService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;
    private String profileRootPath = "profile/";

    @Transactional
    @PostMapping("/comment")
    public WriteCommentResponseDTO writeComment(@RequestBody CommentDTO commentDTO) {
        WriteCommentResponseDTO writeCommentResponseDTO = new WriteCommentResponseDTO("Write Comment FAIL", 500, null);
        System.out.println("댓글 작성 시작...");
        try {
            CommentInfoDTO commentInfoDTO = new CommentInfoDTO();


            System.out.println("일정 id 조회");
            Long curMemberID = memberService.getMemberIdByUserId(getMemberInfoService.getUserIdByJWT());
            Long curTravelID = postService.getTravelIdByPostId(commentDTO.getPostId());
            System.out.println("댓글 저장");
            commentService.saveComment(curMemberID, curTravelID, commentDTO);
            System.out.println("응답 데이터 생성");
            System.out.println("프로필 이미지 확인");
            String defaultProfileImage = "https://" + bucket + ".s3." + region + ".amazonaws.com/"
                + profileRootPath + "default/default_profile_image.png";
            int isExist = profileImageService.isExistProfileUrl(curMemberID);
            if(isExist > 0){
                Long imageId = profileImageService.getImageIdByMemberId(curMemberID);
                defaultProfileImage = imageService.getProfileUrlByImageId(imageId);
            }
            commentInfoDTO.setProfileUrl(defaultProfileImage);
            System.out.println("닉네임 확인");
            commentInfoDTO.setNickName(memberService.getNicknameByMemberId(curMemberID));
            System.out.println("댓글내용 확인");
            commentInfoDTO.setContent(commentDTO.getComcontent());


            writeCommentResponseDTO.setMessage("Write Comment SUCESS");
            writeCommentResponseDTO.setStatus(200);
            writeCommentResponseDTO.setData(commentInfoDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return writeCommentResponseDTO;
    }
}
