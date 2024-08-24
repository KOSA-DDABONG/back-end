package com.ddabong.tripflow.board.service;

import com.ddabong.tripflow.board.dto.*;

import java.util.List;

public interface IBoardService {

    void save(BoardDTO boardDTO);

    List<BoardDTO> findAll();

    void updateHits(Long id);

    BoardDTO findById(Long id);

    BoardDTO findDetail(Long id);

    void update(BoardDTO boardDTO);

    void delete(Long id);
    List<BoardDTO> findLike(Long id);
    //List<BoardDTO> findComment(Long id);
    List<CommentDTO> findComment(Long id);
    List<BoardDTO> findTOP();
    void saveCommnet(CommentDTO commentDTO);

    void savePost(BoardDTO boardDTO);

    List<HashDTO> findHash(Long id);

    void saveImage(ImageDTO imageDTO);
    void savePostImage(PostImageDTO postImageDTO);
    List<ImageDTO> findImage(Long id);

    PostImageDTO findPostid();
    Long findImageid();

    Long findMemberid(String s);

    void saveHash(HashDTO hashDTO);
    void saveHashJoin(HashDTO hashDTO);
    Long findHashid(String s);
    Long findLikeCount(Long id);
    Long findCommentCount(Long id);
    Long findTravelid(Long id);
    void saveLike(MemberDTO memberDTO);
    void deleteLike(MemberDTO memberDTO);
    //Long getMemberIdByUserId(String userIdByJWT);
    Boolean findLikeflag(MemberDTO memberDTO);
}