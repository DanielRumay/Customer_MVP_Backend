package com.rumay.pruebas_customer.service.impl;

import com.rumay.pruebas_customer.dto.post.PostCreateDTO;
import com.rumay.pruebas_customer.dto.post.PostDTO;
import com.rumay.pruebas_customer.dto.post.PostUpdateDTO;
import com.rumay.pruebas_customer.mapper.PostMapper;
import com.rumay.pruebas_customer.model.entity.Post;
import com.rumay.pruebas_customer.model.entity.User;
import com.rumay.pruebas_customer.repository.PostRepository;
import com.rumay.pruebas_customer.repository.UserRepository;
import com.rumay.pruebas_customer.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Transactional
    @Override
    public PostDTO createPost(PostCreateDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        Post post = postMapper.toEntity(dto);
        post.setUser(user);
        Post savedPost = postRepository.save(post);

        return postMapper.toDTO(savedPost);
    }

    public PostDTO updatePost(Long id, PostUpdateDTO dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
        postMapper.updateEntity(dto, post);
        Post updatedPost = postRepository.save(post);
        return postMapper.toDTO(updatedPost);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }
}
