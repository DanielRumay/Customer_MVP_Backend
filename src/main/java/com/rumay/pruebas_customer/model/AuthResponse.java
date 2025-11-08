package com.rumay.pruebas_customer.model;

public class AuthResponse {
    private String token;
    private String nombre;
    private String username;

    public AuthResponse() {}

    public AuthResponse(String token, String nombre, String username) {
        this.token = token;
        this.nombre = nombre;
        this.username = username;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}