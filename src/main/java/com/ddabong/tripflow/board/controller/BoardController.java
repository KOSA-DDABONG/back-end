package com.ddabong.tripflow.board.controller;

import com.ddabong.tripflow.board.dto.BoardDTO;
import com.ddabong.tripflow.board.dto.CommentDTO;
import com.ddabong.tripflow.board.dto.ResponseDTO;
import com.ddabong.tripflow.board.service.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {//클래스명 BoardController
    //아래 코드는 BoardController 클래스가 BoardService 타입의 객체를 의존성으로 가지고 있음을 의미한다.
    //즉 BoardController는 BoardService 객체를 사용하여 게시판 관련 기능을 구현한다.
    //BoardSerivce 에도 동일하게 @RequiredArgsConstructor 어노테이션을 실행해주어야함
    @Autowired
    private IBoardService boardService;

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

    @PostMapping("/list/savecomment")//댓글 저장 기능
    public ResponseEntity<ResponseDTO> saveComment(@RequestBody CommentDTO commentDTO){
        try{
            System.out.println("출력결과:");
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
        ResponseDTO responseDTO = new ResponseDTO("sss",200, commentDTO);
        return  ResponseEntity.ok(responseDTO);
    }

    @Transactional
    @GetMapping("/list") // 좋아요 전체 list를 조회 하는 메소드 // 좋아요 상위3개 추출
    public ResponseEntity<ResponseDTO> findAll() { // json 형식으로 데이터를 반환
        List<BoardDTO> boardDTOList = boardService.findAll();
        List<BoardDTO> boardDTOListtop = boardService.findTOP();

        System.out.println("boardDTOList:" + boardDTOList);
        System.out.println("boardDTOListtop:" + boardDTOListtop);

        // JSON 형식으로 반환할 ResponseDTO 객체 생성
        ResponseDTO responseDTO = new ResponseDTO("success", 200,boardDTOListtop,boardDTOList);
        // ResponseEntity를 통해 JSON 응답 반환
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}") // 조회할 때마다 조회수가 증가하도록 하는 메소드
    public ResponseEntity<BoardDTO> findById(@PathVariable("id") Long id) {
        // 조회수 처리
        // 상세 내용 가져옴
        BoardDTO boardDTO = boardService.findById(id);
        System.out.println("boardDTO = " + boardDTO);

        // BoardDTO 객체를 JSON 형식으로 반환
        return ResponseEntity.ok(boardDTO);
    }

    //이미지를 선택하면 디테일한 데이터를 넘겨주는 데이터(좋아요 갯수, content, 댓글, 해쉬태그...)
    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseDTO> findDetail(@PathVariable("id") Long id){
        List<BoardDTO> boardDTODetail = boardService.findDetail(id); //postid, content
        List<CommentDTO> commentDTO = boardService.findComment(id);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardDTOList.addAll(boardDTODetail);

        ResponseDTO responseDTO = new ResponseDTO("sss",200, boardDTOList,commentDTO);
        return  ResponseEntity.ok(responseDTO);
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