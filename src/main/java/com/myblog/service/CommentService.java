package com.myblog.service;

import com.myblog.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO save(long postId, CommentDTO commentDto);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId, Long commentId);

    public void deleteComment(Long postId, Long commentId);

    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest);
}
