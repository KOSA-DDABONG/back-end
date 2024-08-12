package com.ddabong.tripflow.board.service;

import com.ddabong.tripflow.board.dto.BoardDTO;
import com.ddabong.tripflow.board.dto.CommentDTO;

import java.util.List;

public interface IBoardService {

    void save(BoardDTO boardDTO);

    List<BoardDTO> findAll();

    void updateHits(Long id);

    BoardDTO findById(Long id);

    List<BoardDTO> findDetail(Long id);

    void update(BoardDTO boardDTO);

    void delete(Long id);
    List<BoardDTO> findLike(Long id);
    //List<BoardDTO> findComment(Long id);
    List<CommentDTO> findComment(Long id);
    List<BoardDTO> findTOP();
    void saveCommnet(CommentDTO commentDTO);
}