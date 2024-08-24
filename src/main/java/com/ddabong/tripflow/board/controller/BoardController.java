package com.ddabong.tripflow.board.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ddabong.tripflow.board.dto.*;
import com.ddabong.tripflow.board.service.IBoardService;
import com.ddabong.tripflow.member.service.GetMemberInfoService;
import com.ddabong.tripflow.member.service.IMemberService;
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

    @PostMapping("/save") //데이터를 저장하는 메소드
    // save.html 에서 action="/save" method="post" 로 정의 되어 있기 때문
    public String save(BoardDTO boardDTO) { //BoardDTO(class명,자료형) boardDTO(변수명), 마치 int num 과 같은 느낌
        //위 save와 다른 이유는 파라미터가 다르기 때문! 위는 파라미터가 공란이다.
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO); //이 데이터를 Service, repository mapper를 사용해서 DB로 넘겨야한다.
        return "redirect:/list" ;
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

    //s3 업로드를 위한 데이터
    private final AmazonS3Client amazonS3Client;
    private String postFileUploadPath = "postimg/";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/savepost")//게시물 저장 기능 + 이미 추가 기능 + 이미지S3 업로드 기능까지 구현
    public ResponseEntity<ResponseDTO_SavePost> savePost(@RequestPart BoardDTO boardDTO,
                                                         @RequestPart List<HashDTO> hashDTOList,
                                                         @RequestPart (required = false)List<MultipartFile> files) throws IOException {
        //@RequestBody를 사용하면 하나의 class만 받을 수 있다. 따라서 두개를 통합하는 DTO를 만들었다.
        //두개의 DTO 필드를 하나의 class에 저장하여 Post를 받을 때 새로운 DTO롤 또 다시 생성하지 않고 기존의 코드를 재사용
        System.out.println("userid: " + boardDTO.getUserid());
        //userid를 memberid로 변환
        String ss = boardDTO.getUserid();
        boardDTO.setMemberid(boardService.findMemberid(ss));
        System.out.println("memberid: " + boardDTO.getMemberid());
        //created time 저장
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:s"));
        System.out.println(time);
        boardDTO.setCreatedtime(time);
        try{
            //System.out.println("Postid: " + boardDTO.getPostid());
            System.out.println("travelid: " + boardDTO.getTravelid());
            System.out.println("content: " + boardDTO.getContent());
            System.out.println("memberid: " + boardDTO.getMemberid());
            System.out.println("createdtime: " + boardDTO.getCreatedtime());
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        boardService.savePost(boardDTO);
        PostImageDTO postImageDTO =  boardService.findPostid(); //현재 저장될 postid를 미리 저장하여 postimage 저장 할때 사용

        //ObjectMetadata metadata = new ObjectMetadata();
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


        List<ImageDTO> imageDTOList = new ArrayList<>();
        for(int i = 0 ; i < files.size() ; i++) { // for문을 사용하여 여러 이미지가 들어와도 저장 가능하도록 설계
            //s3 이미지 업로드
            MultipartFile file = files.get(i); // 이미지 리스트에서 한개만 추출
            StringBuffer sb = new StringBuffer();
            sb.append(UUID.randomUUID());
            sb.append("-");
            sb.append(file.getOriginalFilename());
            String originalFilename = postFileUploadPath + sb.toString();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            String fileUrl= "https://" + bucket + originalFilename;
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
        // new를 사용하여 새로운 인스턴스를 생성. new를 사용하면 JVM 메모리 공간에 할당되고 이것을 인스턴스라 한다.
        ResponseDTO_SavePost responseDTO = new ResponseDTO_SavePost("success",200,boardDTO,imageDTOList,hashDTOList);
        return ResponseEntity.ok(responseDTO);
    }

    @Transactional
    @GetMapping("/list") // 좋아요 전체 list를 조회 하는 메소드 // 좋아요 상위3개 추출
    public ResponseEntity<ResponseDTO_BLBL> findAll() { // json 형식으로 데이터를 반환
        List<BoardDTO> boardDTOList = boardService.findAll();
        List<BoardDTO> boardDTOListtop = boardService.findTOP();

        for (int i = 0 ; i < boardDTOList.size() ; i++){
            Long postid = boardDTOList.get(i).getPostid();
            Long likecount = boardService.findLikeCount(postid);
            Long commentcount = boardService.findCommentCount(postid);
            boardDTOList.get(i).setComcontentcount(commentcount);
            boardDTOList.get(i).setLikecount(likecount);
        }

        System.out.println("boardDTOList:" + boardDTOList);
        System.out.println("boardDTOListtop:" + boardDTOListtop);

        // JSON 형식으로 반환할 ResponseDTO 객체 생성
        ResponseDTO_BLBL responseDTO = new ResponseDTO_BLBL("success", 200,boardDTOListtop,boardDTOList);
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

    @GetMapping("/list/{id}") //위 동작하는 데이터 남겨두고 이미지 작업 진행
    public ResponseEntity<ResponseDTO_Listid> findDetail(@PathVariable("id") Long id){

        //System.out.println("whatis this" + getMemberInfoService.getUserIdByJWT());
        //System.out.println("check1");
        //JWT 토큰을 이용해서 userid를 가져옴
        String userid = getMemberInfoService.getUserIdByJWT();
        Long memberid = boardService.findMemberid(userid);
        BoardDTO boardDTO = boardService.findDetail(id); //postid, content
        //현재 사용자 데이터를 통해 좋아요 누른 여부 확인
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberid(memberid);
        memberDTO.setPostid(id);
        System.out.println("findLikeflag " + boardService.findLikeflag(memberDTO));
        boardDTO.setLikeflag(boardService.findLikeflag(memberDTO));
        //댓글 해쉬태그 이미지 저장
        List<CommentDTO> commentDTO = boardService.findComment(id);
        List<HashDTO> hashDTO = boardService.findHash(id); //HASH태그 불러오기
        List<ImageDTO> findiamgeDTO = boardService.findImage(id);

        ResponseDTO_Listid responseDTO = new ResponseDTO_Listid("success",200, boardDTO,hashDTO, commentDTO,findiamgeDTO);
        return  ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/list/{id}/changelike") //후기 상세페이지에서 좋아요 누르기 // flag = 1 좋아요, flag = 0 좋아요 해제
    public  ResponseEntity<ResponseDTO_B> postlike(@PathVariable("id") Long id){
        String userid = getMemberInfoService.getUserIdByJWT();
        Long memberid = boardService.findMemberid(userid);
        //System.out.println("userid: " + userid);
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberid(memberid);
        memberDTO.setPostid(id);

        if(boardService.findLikeflag(memberDTO) == true){//좋아요 없는데 누른경우
            boardService.deleteLike(memberDTO);
        }
        else if(boardService.findLikeflag(memberDTO) == false){//좋아요 있는데 삭제한경우
            boardService.saveLike(memberDTO);
        }
        memberDTO.setLikeflag(boardService.findLikeflag(memberDTO));
        ResponseDTO_B responseDTOB = new ResponseDTO_B("success", 200, memberDTO);
        return  ResponseEntity.ok(responseDTOB);
    }

    @GetMapping("/update/{id}") // update 시 사용
    // URL에서 "id"라는 변수를 가져온다.
    public ResponseEntity<BoardDTO> update(@PathVariable("id") Long id) {
        BoardDTO boardDTO = boardService.findById(id);
        System.out.println("boardDTO = " + boardDTO);
        // BoardDTO 객체를 JSON 형식으로 반환
        return ResponseEntity.ok(boardDTO);
    }

    @PostMapping("/update/{id}") // 데이터를 저장할 때 사용
    public ResponseEntity<BoardDTO> update(@RequestBody BoardDTO boardDTO) {
        boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        // 업데이트된 BoardDTO 객체를 JSON 형식으로 반환
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        // 삭제 성공 메시지를 JSON 형식으로 반환
        return ResponseEntity.ok("삭제 완료");
    }
}