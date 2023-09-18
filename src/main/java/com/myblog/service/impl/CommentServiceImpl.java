package com.myblog.service.impl;


import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.BlogAPIException;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.CommentDTO;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository,ModelMapper mapper)
    {
        this.commentRepository = commentRepository;
        this.mapper=mapper;
    }


    @Override
    public CommentDTO save(long postId, CommentDTO commentDto) {
//        Post post = postRepository.findById(postId).get();
//
//        Comment comment = new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setName(commentDto.getName());
//        comment.setPost(post);
//        Comment newComment = commentRepository.save(comment);
//        CommentDTO dto = new CommentDTO();
//        dto.setCommentId(newComment.getCommentId());
//        dto.setName(newComment.getName());
//        dto.setName(newComment.getName());
//        dto.setEmail(newComment.getEmail());
//        dto.setBody(newComment.getBody());
//        dto.setPostDTO(newComment.getPost());//39:01
//        return dto;


        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId)
        );
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        CommentDTO dto=mapToDto(newComment);
        return dto;

    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",postId)
        );


        List<Comment> comments = commentRepository.findByPostId(postId);
       return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()  ->  new ResourceNotFoundException("Comment","id",commentId));
        if (!(comment.getPost().getId() ==post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        return mapToDto(comment);

    }


    Comment mapToEntity (CommentDTO commentDTO){
       Comment comment= mapper.map(commentDTO,Comment.class);
//        Comment comment=new Comment();
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());

        return comment;
    }
    private CommentDTO mapToDto (Comment newComment){
     CommentDTO commentDTO=   mapper.map(newComment,CommentDTO.class);
        return commentDTO;
    }


    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        if(!(comment.getPost().getId()==(post.getId()))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!(comment.getPost().getId()==(post.getId()))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);

    }





}