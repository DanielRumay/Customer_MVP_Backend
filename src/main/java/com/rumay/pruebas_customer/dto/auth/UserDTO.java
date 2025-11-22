package com.rumay.pruebas_customer.dto.auth;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private String password;
}
