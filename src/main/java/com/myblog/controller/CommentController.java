package com.myblog.controller;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.CommentDTO;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/posts/")
public class CommentController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("{postId}/comments")
    public ResponseEntity<Object> saveComment( @PathVariable Long postId,@Valid @RequestBody CommentDTO commentDto, BindingResult result) {
    if(result.hasErrors()){
        return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
        CommentDTO dto = commentService.save(postId,commentDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }


    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @RequestBody CommentDTO commentDTO){
        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }


    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
}






}



