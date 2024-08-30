package com.ddabong.tripflow.board.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ddabong.tripflow.board.dto.*;
import com.ddabong.tripflow.board.service.IBoardService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor

@Controller
public class BoardController {//클래스명 BoardController
    //아래 코드는 BoardController 클래스가 BoardService 타입의 객체를 의존성으로 가지고 있음을 의미한다.
    //즉 BoardController는 BoardService 객체를 사용하여 게시판 관련 기능을 구현한다.
    //BoardSerivce 에도 동일하게 @RequiredArgsConstructor 어노테이션을 실행해주어야함
    @Autowired
    private IBoardService boardService;
    @Autowired
    private GetMemberInfoService getMemberInfoService;

    //아래 save는 모두 다른 save이다.
    @GetMapping("/save") //save 주소 입력시
    public String save() { //java 메소드 이름이 save
        return "save"; //return 할 화면의 이름
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO) {
        System.out.println(boardDTO);
        boardService.save(boardDTO);
        return "redirect:/list" ;
    }

    //s3 업로드를 위한 데이터
private final AmazonS3Client amazonS3Client;
private String postFileUploadPath = "postimg/";
@Value("${cloud.aws.s3.bucket}")
private String bucket;

    @GetMapping("/saveimg") // s3에 저장된 이미지를 자동으로 post와 매칭 postimg 내 폴더 번호가 postid
    public  String saveimg(){
        System.out.println("hello");
        return "fin";
    }


    @PostMapping("/list/{id}/savecomment")//댓글 저장 기능 + 완료
    public ResponseEntity<ResponseDTO_SaveComment> saveComment(@PathVariable("id") Long id, @RequestPart CommentDTO commentDTO)throws IOException{
        //postid 입력 및 postid로 memberid 까지 검색
        commentDTO.setPostid(id);
        commentDTO.setTravelid(boardService.findTravelid(id));
        //userid로 memberid 검색 후 입력
        String ss = commentDTO.getUserid();
        commentDTO.setMemberid(boardService.findMemberid(ss));
        // 생성 시간 저장
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:s"));
        System.out.println(time);
        commentDTO.setCreatedtime(time);

        try{
            System.out.println("commentid: " + commentDTO.getCommentid());
            System.out.println("postid: " + commentDTO.getPostid());
            System.out.println("travelid: " + commentDTO.getTravelid());
            System.out.println("Commentid2: "+ commentDTO.getCommentid2());
            System.out.println("Memberid: " + commentDTO.getMemberid());
            System.out.println("Comcontent: " + commentDTO.getComcontent());
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        boardService.saveCommnet(commentDTO);
        ResponseDTO_SaveComment responseDTO = new ResponseDTO_SaveComment("success",200, commentDTO);
        return  ResponseEntity.ok(responseDTO);
    }

    @Transactional
    @PostMapping("/savepost")
    public ResponseEntity<ResponseDTO_SavePost> savePost(
            @RequestPart BoardDTO boardDTO,
            @RequestPart List<HashDTO> hashDTOList,
            @RequestPart (required = false)List<MultipartFile> files) throws IOException {


        String ss = boardDTO.getUserid();
        boardDTO.setMemberid(boardService.findMemberid(ss));
        System.out.println("memberid: " + boardDTO.getMemberid());
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:s"));
        System.out.println(time);
        boardDTO.setCreatedtime(time);
        try{
            System.out.println("travelid: " + boardDTO.getTravelid());
            System.out.println("content: " + boardDTO.getContent());
            System.out.println("memberid: " + boardDTO.getMemberid());
            System.out.println("createdtime: " + boardDTO.getCreatedtime());
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        boardService.savePost(boardDTO);
        PostImageDTO postImageDTO =  boardService.findPostid(); //현재 저장될 postid를 미리 저장하여 postimage 저장 할때 사용

        System.out.println("hashDTO길이: " + hashDTOList.size());
        for(int i = 0 ; i < hashDTOList.size() ; i++){
            HashDTO hashDTO_tmp = new HashDTO();
            hashDTO_tmp = hashDTOList.get(i);
            boardService.saveHash(hashDTO_tmp);
            Long hashid = boardService.findHashid(hashDTO_tmp.getHashname());
            hashDTO_tmp.setHashtagid(hashid);
            hashDTO_tmp.setPostid(postImageDTO.getPostid());
            hashDTO_tmp.setTravelid(postImageDTO.getTravelid());
            System.out.println("Hashtmp" + hashDTO_tmp);
            boardService.saveHashJoin(hashDTO_tmp);
            hashDTOList.set(i ,hashDTO_tmp);
        }

        String tmp = ".s3.ap-northeast-2.amazonaws.com/";
        List<ImageDTO> imageDTOList = new ArrayList<>();
        for(int i = 0 ; i < files.size() ; i++) {

            MultipartFile file = files.get(i);
            StringBuffer sb = new StringBuffer();
            sb.append(UUID.randomUUID());
            sb.append("-");
            sb.append(file.getOriginalFilename());
            String originalFilename = postFileUploadPath + sb.toString();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            String fileUrl= "https://" + bucket + tmp + originalFilename;
            fileUrl = fileUrl.replace(" ", "%20");
            System.out.println(i + "fileUrl: " + fileUrl);
            System.out.println(i + "riginalFilename!: " + originalFilename);
            amazonS3Client.putObject(bucket, originalFilename, file.getInputStream(), metadata); // s3 업로드

            //sql 이미지 데이터 입력
            ImageDTO saveImagetmp = new ImageDTO();
            saveImagetmp.setFilename(originalFilename);
            saveImagetmp.setUrl(fileUrl);
            saveImagetmp.setImagetype(3L); //후기 사진이므로3으로 설정
            imageDTOList.add(saveImagetmp); //이미지 데이터 Response확인용
            boardService.saveImage(saveImagetmp); //단일 이미지 데이터 저장
            Long imageid= boardService.findImageid(); // 저장된 이미지 id 추출
            postImageDTO.setImageid(imageid); // id를 postimagedto에 저장
            boardService.savePostImage(postImageDTO);// imageid, postid, travelid아이디를 사용해서 저장
        }

        ResponseDTO_SavePost responseDTO = new ResponseDTO_SavePost("success",200,boardDTO,imageDTOList,hashDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @Transactional
    @GetMapping("/list") // 좋아요 전체 list를 조회 하는 메소드 // 좋아요 상위3개 추출
    public ResponseEntity<ResponseDTO_AllList> findAll() { // json 형식으로 데이터를 반환


        String userid = getMemberInfoService.getUserIdByJWT();
        Long memberid = boardService.findMemberid(userid);

        List<BoardDTO> boardDTOList = boardService.findAll();
        List<BoardDTO> boardDTOListtop = boardService.findTOP();
        List<BoardDTO_View> boardDTOViews_Top = new ArrayList<>();
        List<BoardDTO_View> boardDTOViews = new ArrayList<>();
        // 좋아요 수 상위 TOP3  출력
        for (int i = 0 ; i < boardDTOListtop.size() ; i++){
            MemberDTO memberDTO = new MemberDTO();
            BoardDTO_View boardDTOView = new BoardDTO_View();
            Long postid = boardDTOListtop.get(i).getPostid();
            memberDTO.setMemberid(memberid);
            memberDTO.setPostid(boardDTOListtop.get(i).getPostid());
            // 게시판 관련 데이터 조회
            boardDTOView.setComcontentcount(boardService.findCommentCount(boardDTOListtop.get(i).getPostid()));
            boardDTOView.setPostid(postid);
            boardDTOView.setLikeflag(boardService.findLikeflag(memberDTO));
            boardDTOView.setCreatetime(boardService.findCreatetime(boardDTOListtop.get(i).getPostid()));
            boardDTOView.setLikecount(boardDTOListtop.get(i).getLikecount());

            //이미지 url 조회
            List<ImageDTO> findiamgeDTO = boardService.findImage(postid);
            if(!findiamgeDTO.isEmpty()) {
                boardDTOView.setImgurl(findiamgeDTO.get(0).getUrl());
            }
            boardDTOViews_Top.add(boardDTOView);
        }

        //전체 리스트 출력 마지막으로 저장한 순서대로
        for (int i = 0 ; i < boardDTOList.size() ; i++){
            MemberDTO memberDTO = new MemberDTO();
            BoardDTO_View boardDTOView = new BoardDTO_View();
            Long postid = boardDTOList.get(i).getPostid();
            memberDTO.setMemberid(memberid);
            memberDTO.setPostid(postid);

            boardDTOView.setComcontentcount(boardService.findCommentCount(postid));
            boardDTOView.setPostid(boardDTOList.get(i).getPostid());
            boardDTOView.setLikeflag(boardService.findLikeflag(memberDTO));
            boardDTOView.setCreatetime(boardService.findCreatetime(postid));
            boardDTOView.setLikecount(boardService.findLikeCount(postid));

            List<ImageDTO> findiamgeDTO = boardService.findImage(postid);
            if(!findiamgeDTO.isEmpty()) {
                boardDTOView.setImgurl(findiamgeDTO.get(0).getUrl());
            }

            boardDTOViews.add(boardDTOView);
        }



        System.out.println("boardDTOList:" + boardDTOList);
        System.out.println("boardDTOListtop:" + boardDTOListtop);

        // JSON 형식으로 반환할 ResponseDTO 객체 생성
        ResponseDTO_AllList responseDTO = new ResponseDTO_AllList("success", 200,boardDTOViews_Top,boardDTOViews);
        // ResponseEntity를 통해 JSON 응답 반환
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/mylist")
    public ResponseEntity<ResponseDTO_AllList> findMyList() {
        String userid = getMemberInfoService.getUserIdByJWT();
        Long memberid = boardService.findMemberid(userid);
        List<BoardDTO> boardDTOList = boardService.findAll();
        List<BoardDTO_View> boardDTOViews = new ArrayList<>();
        List<BoardDTO_View> boardDTOViewstmp = new ArrayList<>();



        //전체 리스트 출력을 postid가 높은 순서대로(최신순)
        for (int i = 0 ; i < boardDTOList.size() ; i++){
            MemberDTO memberDTO = new MemberDTO();
            BoardDTO_View boardDTOView = new BoardDTO_View();
            Long postid = boardDTOList.get(i).getPostid();
            memberDTO.setMemberid(memberid);
            memberDTO.setPostid(postid);
            if(boardService.findLikeflag(memberDTO)) {
                boardDTOView.setComcontentcount(boardService.findCommentCount(postid));
                boardDTOView.setPostid(boardDTOList.get(i).getPostid());
                boardDTOView.setLikeflag(boardService.findLikeflag(memberDTO));
                boardDTOView.setCreatetime(boardService.findCreatetime(postid));
                boardDTOView.setLikecount(boardService.findLikeCount(postid));

                List<ImageDTO> findiamgeDTO = boardService.findImage(postid);
                if (!findiamgeDTO.isEmpty()) {
                    boardDTOView.setImgurl(findiamgeDTO.get(0).getUrl());
                }
                boardDTOViews.add(boardDTOView);
            }
        }

        System.out.println("boardDTOList:" + boardDTOList);

        // JSON 형식으로 반환할 ResponseDTO 객체 생성
        ResponseDTO_AllList responseDTO = new ResponseDTO_AllList("success", 200, boardDTOViews,boardDTOViewstmp);
        // ResponseEntity를 통해 JSON 응답 반환
        return ResponseEntity.ok(responseDTO);
    }




    @GetMapping("/{id}") // 조회할 때마다 조회수가 증가하도록 하는 메소드 //사용 안할 예정
    public ResponseEntity<BoardDTO> findById(@PathVariable("id") Long id) {
        // 조회수 처리
        // 상세 내용 가져옴
        BoardDTO boardDTO = boardService.findById(id);
        System.out.println("boardDTO = " + boardDTO);

        // BoardDTO 객체를 JSON 형식으로 반환
        return ResponseEntity.ok(boardDTO);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseDTO_Listid> findDetail(
            @PathVariable("id") Long id){

        //System.out.println("whatis this" + getMemberInfoService.getUserIdByJWT());
        //System.out.println("check1");
        //JWT 토큰을 이용해서 userid를 가져옴
        String userid = getMemberInfoService.getUserIdByJWT();
        Long memberid = boardService.findMemberid(userid);
        BoardDTO boardDTO = boardService.findDetail(id); //postid, content
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberid(memberid);
        memberDTO.setPostid(id);
        System.out.println("findLikeflag " + boardService.findLikeflag(memberDTO));
        boardDTO.setLikeflag(boardService.findLikeflag(memberDTO));
        //댓글 해쉬태그 이미지 저장
        List<CommentDTO> commentDTO = boardService.findComment(id);
        List<HashDTO> hashDTO = boardService.findHash(id); //HASH태그 불러오기
        List<ImageDTO> findimageDTO = boardService.findImage(id);

        ResponseDTO_Listid responseDTO = new ResponseDTO_Listid(
                "success",200, boardDTO,hashDTO, commentDTO,findimageDTO);
        return  ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/list/{id}/update") // 데이터 수정
    public ResponseEntity<ResponseDTO_SavePost> update(
            @PathVariable("id") Long id,
            @RequestPart BoardDTO boardDTO,
            @RequestPart (required = false)List<MultipartFile> files,
            @RequestPart List<HashDTO> hashDTOList) throws IOException {

        Long memberid = boardService.findMemberid(boardDTO.getUserid());
        Long travelid = boardService.findTravelid(id);
        DeletePostDTO deletePostDTO = new DeletePostDTO();
        deletePostDTO.setMemberid(memberid);
        deletePostDTO.setTravelid(travelid);
        deletePostDTO.setPostid(id);
        List<ImageDTO> imageDTOList = new ArrayList<>();
        if ( memberid == boardService.findMemberidInPost(id)) {
            boardDTO.setPostid(id);
            boardService.updatePost(boardDTO);
            boardService.deleteHashtagJoin(deletePostDTO);

            for (int i = 0; i < hashDTOList.size(); i++) {
                HashDTO hashDTO_tmp = new HashDTO();
                hashDTO_tmp = hashDTOList.get(i);
                boardService.saveHash(hashDTO_tmp);
                Long hashid = boardService.findHashid(hashDTO_tmp.getHashname());
                hashDTO_tmp.setHashtagid(hashid);
                hashDTO_tmp.setPostid(id);
                hashDTO_tmp.setTravelid(travelid);
                boardService.saveHashJoin(hashDTO_tmp);
                hashDTOList.set(i, hashDTO_tmp);
            }

            if(!files.isEmpty()) { //이미지가 한개 이상 들어온 경우
                boardService.deleteImage(id);//기존에 저장된 이미지를 다 지우고
                //새로운 이미지를 저장
                PostImageDTO postImageDTO = boardService.findPostid(); //현재 저장될 postid를 미리 저장하여 postimage 저장 할때 사용
                String tmp = ".s3.ap-northeast-2.amazonaws.com/";
                for (int i = 0; i < files.size(); i++) { // for문을 사용하여 여러 이미지가 들어와도 저장 가능하도록 설계
                    //s3 이미지 업로드
                    MultipartFile file = files.get(i); // 이미지 리스트에서 한개만 추출
                    StringBuffer sb = new StringBuffer();
                    sb.append(UUID.randomUUID());
                    sb.append("-");
                    sb.append(file.getOriginalFilename());

                    ObjectMetadata metadata = new ObjectMetadata();
                    String originalFilename = postFileUploadPath + sb.toString();
                    metadata.setContentLength(file.getSize());
                    metadata.setContentType(file.getContentType());
                    String fileUrl = "https://" + bucket + tmp + originalFilename;
                    fileUrl = fileUrl.replace(" ", "%20");
                    System.out.println(i + "fileUrl: " + fileUrl);
                    System.out.println(i + "riginalFilename!: " + originalFilename);
                    amazonS3Client.putObject(bucket, originalFilename, file.getInputStream(), metadata); // s3 업로드

                    //sql 이미지 데이터 입력
                    ImageDTO saveImagetmp = new ImageDTO();
                    saveImagetmp.setFilename(originalFilename);
                    saveImagetmp.setUrl(fileUrl);
                    saveImagetmp.setImagetype(3L); //후기 사진이므로3으로 설정
                    imageDTOList.add(saveImagetmp); //이미지 데이터 Response확인용
                    boardService.saveImage(saveImagetmp); //단일 이미지 데이터 저장
                    Long imageid = boardService.findImageid(); // 저장된 이미지 id 추출
                    postImageDTO.setImageid(imageid); // id를 postimagedto에 저장
                    boardService.savePostImage(postImageDTO);// imageid, postid, travelid아이디를 사용해서 저장
                }
            }
            ResponseDTO_SavePost responseDTO = new ResponseDTO_SavePost("success",200,boardDTO,imageDTOList,hashDTOList);
            return ResponseEntity.ok(responseDTO);
        }
        ResponseDTO_SavePost responseDTO = new ResponseDTO_SavePost("Fail 게시글 작성자가 아닙니다.",200,boardDTO,imageDTOList,hashDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/list/{id}/changelike") //후기 상세페이지에서 좋아요 누르기 // flag = 1 좋아요, flag = 0 좋아요 해제
    public  ResponseEntity<ResponseDTO_B> postlike(@PathVariable("id") Long id){
        String userid = getMemberInfoService.getUserIdByJWT();
        Long memberid = boardService.findMemberid(userid);
        //System.out.println("userid: " + userid);
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberid(memberid);
        memberDTO.setPostid(id);
        String member_birth = boardService.findBirth(memberid);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:s"));
        String current_year = time.substring(0,4);
        System.out.println("current_year" + current_year);
        Long age = Long.parseLong(current_year) - Long.parseLong(member_birth.substring(0,4));
        Long travelid = boardService.findTravelid(id);
        System.out.println("travelid" + travelid);
        Long placeId = boardService.findPlaceId(travelid);
        RecommendPlaceDTO recommendPlaceDTO = new RecommendPlaceDTO();
        System.out.println("travelid: " + travelid);

        recommendPlaceDTO.setAge(age);
        recommendPlaceDTO.setPlaceid(travelid);

        if(boardService.findLikeflag(memberDTO)){//좋아요 있어 삭제
            boardService.deleteLike(memberDTO);
            boardService.increaseRecommend(recommendPlaceDTO);
        }
        else if(!boardService.findLikeflag(memberDTO)){//좋아요 없어 증가
            boardService.saveLike(memberDTO);
            boardService.increaseRecommend(recommendPlaceDTO);
        }
        memberDTO.setLikeflag(boardService.findLikeflag(memberDTO));
        ResponseDTO_B responseDTOB = new ResponseDTO_B("success", 200, memberDTO);
        return  ResponseEntity.ok(responseDTOB);
    }
    @GetMapping("/list/{id}/delete")//post 게시물 삭제// id = postid
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        String userid = getMemberInfoService.getUserIdByJWT();
        Long memberid = boardService.findMemberid(userid);
        Long travelid = boardService.findTravelid(id);

        //게시글 작성자인 경우
        System.out.println(memberid);
        System.out.println("작성자 "+ boardService.findMemberidInPost(id));
        if(boardService.findMemberidInPost(id) == null) {
            String s = id + " 번 게시글이 존재하지 않습니다.";
            ResponseDTO responseDTO = new ResponseDTO("success", 400, s);
            return ResponseEntity.ok(responseDTO);
        }
        if ( memberid == boardService.findMemberidInPost(id)) {
            DeletePostDTO deletePostDTO = new DeletePostDTO();

            System.out.println("travelid"+travelid);

            deletePostDTO.setMemberid(memberid);
            deletePostDTO.setTravelid(travelid);
            deletePostDTO.setPostid(id);
            boardService.deleteComment(deletePostDTO);
            boardService.deletePostImage(deletePostDTO);
            boardService.deleteHashtagJoin(deletePostDTO);
            boardService.deletePost(deletePostDTO);

            String s = id + " 번 게시물 삭제 완료";
            ResponseDTO responseDTO = new ResponseDTO("success", 200, s);
            return ResponseEntity.ok(responseDTO);
        }
        else{
            String s = id + " 번 게시물 작성자가 아닙니다.";
            ResponseDTO responseDTO = new ResponseDTO("success", 403, s);
            return ResponseEntity.ok(responseDTO);
        }
    }
}