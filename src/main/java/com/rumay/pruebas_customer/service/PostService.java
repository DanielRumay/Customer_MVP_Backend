package com.rumay.pruebas_customer.service;

import com.rumay.pruebas_customer.dto.post.PostCreateDTO;
import com.rumay.pruebas_customer.dto.post.PostDTO;
import com.rumay.pruebas_customer.dto.post.PostUpdateDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO createPost(PostCreateDTO post);
    PostDTO updatePost(Long id, PostUpdateDTO post);
}
