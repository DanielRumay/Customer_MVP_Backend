package com.rumay.pruebas_customer.model.dto;

public class CrearPublicacionDTO {

    private String titulo;
    private String contenido;
    private String imagenUrl;

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
