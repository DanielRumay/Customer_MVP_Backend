package com.rumay.pruebas_customer.dto.post;

import lombok.Data;

@Data
public class PostUpdateDTO {
    private String title;
    private String content;
    private String imagenUrl;
}
