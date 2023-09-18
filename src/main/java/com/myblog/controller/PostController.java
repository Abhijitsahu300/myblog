package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
      if(result.hasErrors()){
           return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
      }

       PostDto dto = postService.createPost(postDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);

   }

   //http://localhost:8080/api/posts?pageNo=0&sortBy=title&sortDir=asc

    //2023/02/08
 @GetMapping
   public PostResponse getAllPost(
           @RequestParam(value="pageNo",defaultValue ="0",required = false) int pageNo,
           @RequestParam(value="pageSize",defaultValue ="10",required = false) int pageSize,
           @RequestParam(value="sortBy",defaultValue ="id",required = false) String sortBy,
           @RequestParam(value="sortDir",defaultValue ="asc",required = false) String sortDir
           )  {


        return postService.getAllPosts( pageNo,pageSize,sortBy,sortDir);

   }

   //http://localhost:8080/api/posts/1
   @GetMapping("/{id}")
   public ResponseEntity<PostDto> getPostBsyId(@PathVariable("id") long id){
       PostDto dto = postService.getPostById(id);
   return ResponseEntity.ok(dto);
   }


    //http://localhost:8080/api/posts/1
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id")long id){
        PostDto Dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(Dto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String >deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return  new ResponseEntity<>("post entity deleted !",HttpStatus.OK);

    }




}
