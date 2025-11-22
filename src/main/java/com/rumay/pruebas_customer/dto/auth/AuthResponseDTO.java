package com.rumay.pruebas_customer.dto.auth;
import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String username;
    private String role;
}
