package com.rumay.pruebas_customer.service.impl;

import com.rumay.pruebas_customer.dto.auth.AuthResponseDTO;
import com.rumay.pruebas_customer.dto.auth.LoginDTO;
import com.rumay.pruebas_customer.dto.auth.UserDTO;
import com.rumay.pruebas_customer.mapper.UserMapper;
import com.rumay.pruebas_customer.model.entity.User;
import com.rumay.pruebas_customer.model.enums.Role;
import com.rumay.pruebas_customer.repository.UserRepository;
import com.rumay.pruebas_customer.security.TokenProvider;
import com.rumay.pruebas_customer.security.UserPrincipal;
import com.rumay.pruebas_customer.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public UserDTO registerUser(UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User newUser = userMapper.toEntity(userDTO);
        newUser.setRole(Role.USER);
        return userMapper.toDto(userRepository.save(newUser));
    }

    @Transactional
    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el id: "+id));
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public AuthResponseDTO loginUser(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUser(), loginDTO.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);

        AuthResponseDTO authResponseDTO = userMapper.toAuthResponseDTO(user,token);

        return authResponseDTO;
    }
}
