package com.ddabong.tripflow.post.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ddabong.tripflow.hashtag.dto.HashtagJoinDTO;
import com.ddabong.tripflow.hashtag.service.IHashtagJoinService;
import com.ddabong.tripflow.hashtag.service.IHashtagService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.post.dto.PostDTO;
import com.ddabong.tripflow.post.dto.WritePostResponseDTO;
import com.ddabong.tripflow.post.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("/post")
public class WriteReviewController {

    @Autowired
    private GetMemberInfoService getMemberInfoService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IHashtagService hashtagService;
    @Autowired
    private IHashtagJoinService hashtagJoinService;
    @Autowired
    private AmazonS3Client amazonS3Client;
    private String fileUploadPath = "postimg/";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Transactional
    @PostMapping("/board/saveReview")
    public WritePostResponseDTO savePost(@RequestParam("postDTO") String postDTOJson,
                                         @RequestParam("files")List<MultipartFile> multipartFiles){
        WritePostResponseDTO writePostResponseDTO = new WritePostResponseDTO("SAVE FAIL", 500);
        System.out.println("후기 저장 시작....");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PostDTO postDTO = objectMapper.readValue(postDTOJson, PostDTO.class);

            System.out.println("-post 테이블 데이터 추가 중...");
            saveDataInPostTable(postDTO, getMemberInfoService.getUserIdByJWT());

            System.out.println("--신규 hash tag 저장 중...");
            List<Long> hashtagList = saveHashtagDataInHashtagTable(postDTO.getHashtags());

            System.out.println("---hash tag 저장 중...");
            saveHashtagInHashtagJoinTable(postDTO.getTravelId(), hashtagList);

            System.out.println("----이미지 저장 중...");
            saveImageFileInImageTable(postDTO.getTravelId(), multipartFiles);

            System.out.println("게시글 저장 완료");

            writePostResponseDTO.setMessage("SAVE REVIEW SUCCESS");
            writePostResponseDTO.setStatus(200);
        } catch (Exception e){
            e.printStackTrace();
        }

        return writePostResponseDTO;
    }

    private void saveImageFileInImageTable(Long travelId, List<MultipartFile> multipartFiles) {
        try {
            Long curPostId = postService.getPostIdByTravelId(travelId);
            List<String> imageUrls = new ArrayList<>();

            for(MultipartFile file : multipartFiles){

                StringBuffer sb = new StringBuffer();
                sb.append(UUID.randomUUID());
                sb.append("-");
                sb.append(file.getOriginalFilename());
                String originalFilename = fileUploadPath + curPostId.toString() + "/" + sb.toString();

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                metadata.setContentType(file.getContentType());

                String fileUrl= "https://" + bucket + ".s3." + region + ".amazonaws.com/" + originalFilename;
                amazonS3Client.putObject(bucket,
                        originalFilename,
                        file.getInputStream(),
                        metadata);

                System.out.println("파일명 : " + fileUrl);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHashtagInHashtagJoinTable(Long travelId, List<Long> hashtagList) {
        Long curPostId = postService.getPostIdByTravelId(travelId);
        HashtagJoinDTO hashtagJoinDTO = new HashtagJoinDTO();
        hashtagJoinDTO.setPostId(curPostId);
        hashtagJoinDTO.setTravelId(travelId);
        hashtagJoinDTO.setHashtagIDs(hashtagList);

        hashtagJoinService.save(hashtagJoinDTO);
    }

    private List<Long> saveHashtagDataInHashtagTable(List<String> hashtags) {
        return hashtagService.isExistHashtag(hashtags);
    }

    private void saveDataInPostTable(PostDTO postDTO, String userIdByJWT) {
        postService.saveReview(postDTO, getMemberInfoService.getUserIdByJWT());
    }
}
