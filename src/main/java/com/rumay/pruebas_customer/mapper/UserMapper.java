package com.rumay.pruebas_customer.mapper;

import com.rumay.pruebas_customer.dto.auth.AuthResponseDTO;
import com.rumay.pruebas_customer.dto.auth.LoginDTO;
import com.rumay.pruebas_customer.dto.auth.UserDTO;
import com.rumay.pruebas_customer.model.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    public User toUserEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }
    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);
        authResponseDTO.setUsername(user.getUsername());
        authResponseDTO.setRole(user.getRole().name());
        return authResponseDTO;
    }
}