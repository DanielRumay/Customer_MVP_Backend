package com.rumay.pruebas_customer.model;

import jakarta.persistence.*;

@Entity
public class Reaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean likeActive;
    private boolean dislikeActive;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    public Long getId() {
        return id;
    }

    public boolean isLikeActive() {
        return likeActive;
    }

    public boolean isDislikeActive() {
        return dislikeActive;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setLikeActive(boolean likeActive) {
        this.likeActive = likeActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDislikeActive(boolean dislikeActive) {
        this.dislikeActive = dislikeActive;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
