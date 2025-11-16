package com.rumay.pruebas_customer.model.dto;

import java.time.LocalDateTime;

public class PublicacionResponseDTO {

    public Long id;
    public String titulo;
    public String contenido;
    public String imagenUrl;
    public LocalDateTime fecha;
    public String autor;
    public boolean likeActive;
    public boolean dislikeActive;
}
