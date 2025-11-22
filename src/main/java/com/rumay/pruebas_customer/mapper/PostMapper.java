package com.rumay.pruebas_customer.mapper;

import com.rumay.pruebas_customer.dto.post.PostCreateDTO;
import com.rumay.pruebas_customer.dto.post.PostDTO;
import com.rumay.pruebas_customer.dto.post.PostUpdateDTO;
import com.rumay.pruebas_customer.model.entity.Post;
import com.rumay.pruebas_customer.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Post toEntity(PostCreateDTO dto) {
        return modelMapper.map(dto, Post.class);
    }

    public void updateEntity(PostUpdateDTO dto, Post post) {
        modelMapper.map(dto, post);
    }

    public PostDTO toDTO(Post post) {
        PostDTO dto = modelMapper.map(post, PostDTO.class);

        if (post.getUser() != null) {
            dto.setUserName(post.getUser().getName());
        }

        return dto;
    }
}
