package com.ddabong.tripflow.board.dao;

import com.ddabong.tripflow.board.dto.BoardDTO;
import com.ddabong.tripflow.board.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardRepository {
    void save(BoardDTO boardDTO);
    List<BoardDTO> findAll();
    //조회수 횟수를 update
    void updateHits(Long id);
    BoardDTO findById(Long id);
    List<BoardDTO>findLike(Long id);
    List<BoardDTO>findDetail(Long id);
    void update(BoardDTO boardDTO);
    void delete(Long id);
    //List<BoardDTO>findComment(Long id);
    List<CommentDTO>findComment(Long id);

    List<BoardDTO> findTOP();
    void saveComment(CommentDTO commentDTO);
}