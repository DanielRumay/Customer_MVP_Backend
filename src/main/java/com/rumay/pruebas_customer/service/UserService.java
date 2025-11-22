package com.rumay.pruebas_customer.service;

import com.rumay.pruebas_customer.dto.auth.AuthResponseDTO;
import com.rumay.pruebas_customer.dto.auth.LoginDTO;
import com.rumay.pruebas_customer.dto.auth.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO user);
    UserDTO updateUser(Integer id, UserDTO user);
    AuthResponseDTO loginUser(LoginDTO loginDTO);
}