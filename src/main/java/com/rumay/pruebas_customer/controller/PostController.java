package com.rumay.pruebas_customer.controller;

import com.rumay.pruebas_customer.dto.post.PostCreateDTO;
import com.rumay.pruebas_customer.dto.post.PostDTO;
import com.rumay.pruebas_customer.dto.post.PostUpdateDTO;
import com.rumay.pruebas_customer.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("create")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostCreateDTO post){
        PostDTO postDTO = postService.createPost(post);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateDTO post){
        PostDTO postDTO = postService.updatePost(id, post);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        List<PostDTO> postDTO = postService.getAllPosts();
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }
}
