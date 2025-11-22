package com.rumay.pruebas_customer.controller;

import com.rumay.pruebas_customer.dto.auth.AuthResponseDTO;
import com.rumay.pruebas_customer.dto.auth.LoginDTO;
import com.rumay.pruebas_customer.dto.auth.UserDTO;
import com.rumay.pruebas_customer.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO user){
        UserDTO userDTO = userService.registerUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        AuthResponseDTO authResponseDTO = userService.loginUser(loginDTO);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
