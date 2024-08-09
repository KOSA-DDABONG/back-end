package com.ddabong.tripflow.board.service;

import com.ddabong.tripflow.board.dao.IBoardRepository;
import com.ddabong.tripflow.board.dto.BoardDTO;
import com.ddabong.tripflow.board.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return boardRepository.findDetail(id);
    }

    public List<BoardDTO> findLike(Long id){
        return boardRepository.findLike(id);
    }

//    public List<BoardDTO> findLikeRep(Long id) {
//        //Ddabong ddabong = findLike(id);
//        List<BoardDTO> boardDTOList = new ArrayList<>();
//
//        for(){
//            BoardDTO boardDTO = new BoardDTO();
//            ser~~~;
//            boardDTOList.add(boardDTO);
//        }
//
//
//        return boardDTOList;
//    }

    //public List<BoardDTO> findComment(Long id){return boardRepository.findComment(id);}
    public List<CommentDTO> findComment(Long id){return boardRepository.findComment(id);}

    public List<BoardDTO> findTOP() {
        return boardRepository.findTOP();
    }

    public void update(BoardDTO boardDTO){
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

}
