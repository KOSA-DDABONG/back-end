package com.ddabong.tripflow.board.service;

import com.ddabong.tripflow.board.dao.IBoardRepository;
import com.ddabong.tripflow.board.dto.*;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ToString
public class BoardService implements IBoardService {

    //IBoardRepository를 boardRepository로 이름 변경
    @Autowired
    private IBoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        boardRepository.save(boardDTO);
    }

    public List<BoardDTO> findAll() {
         return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public List<BoardDTO> findDetail(Long id){
        System.out.println("check2");
        return boardRepository.findDetail(id);
    }

    public List<BoardDTO> findLike(Long id){
        return boardRepository.findLike(id);
    }

    //public List<BoardDTO> findComment(Long id){return boardRepository.findComment(id);}
    public List<CommentDTO> findComment(Long id){return boardRepository.findComment(id);}

    public List<BoardDTO> findTOP() {
        return boardRepository.findTOP();
    }
    public List<HashDTO> findHash(Long id) {
        return boardRepository.findHash(id);}
    public void saveCommnet(CommentDTO commentDTO) {
        boardRepository.saveComment(commentDTO);
    }

    public void update(BoardDTO boardDTO){
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public void savePost(BoardDTO boardDTO) {boardRepository.savePost(boardDTO);}

    //이미지 후기작성 저장시 사용
    public void saveImage(ImageDTO imageDTO) {boardRepository.saveImage(imageDTO);}
    public void savePostImage(PostImageDTO postImageDTO) {boardRepository.savePostImage(postImageDTO);}
    //이미지 조회
    public List<ImageDTO> findImage(Long id){ return boardRepository.findImage(id);}
    public Long findImageid(){return boardRepository.findImageid();}
    public PostImageDTO findPostid(){return boardRepository.findPostid();}

    public Long findMemberid(String s){ return boardRepository.findMemberid(s);}

    public void saveHash(HashDTO hashDTO){boardRepository.saveHash(hashDTO);}
    public void saveHashJoin(HashDTO hashDTO){boardRepository.saveHashJoin(hashDTO);}
    public Long findHashid(String s){return boardRepository.findHashid(s);}

    public Long findLikeCount(Long id){return boardRepository.findLikeCount(id);}
    public Long findCommentCount(Long id){return boardRepository.findCommentCount(id);}
}
