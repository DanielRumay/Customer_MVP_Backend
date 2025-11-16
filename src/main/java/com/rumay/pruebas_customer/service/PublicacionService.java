package com.rumay.pruebas_customer.service;

import com.rumay.pruebas_customer.model.Publicacion;
import com.rumay.pruebas_customer.model.Usuario;
import com.rumay.pruebas_customer.model.dto.CrearPublicacionDTO;
import com.rumay.pruebas_customer.model.dto.PublicacionResponseDTO;
import com.rumay.pruebas_customer.repository.PublicacionRepository;
import com.rumay.pruebas_customer.repository.ReaccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final ReaccionRepository reaccionRepository;

    public PublicacionService(PublicacionRepository pRepo, ReaccionRepository rRepo) {
        this.publicacionRepository = pRepo;
        this.reaccionRepository = rRepo;
    }

    // Crear publicación
    public Publicacion crearPublicacion(CrearPublicacionDTO dto, Usuario usuario) {
        Publicacion p = new Publicacion();
        p.setTitulo(dto.getTitulo());
        p.setContenido(dto.getContenido());
        p.setImagenUrl(dto.getImagenUrl());
        p.setUsuario(usuario);

        return publicacionRepository.save(p);
    }

    // Listar publicaciones públicas
    public List<PublicacionResponseDTO> listarPublico() {
        return publicacionRepository.findAll()
                .stream()
                .map(p -> {
                    PublicacionResponseDTO dto = new PublicacionResponseDTO();
                    dto.id = p.getId();
                    dto.titulo = p.getTitulo();
                    dto.contenido = p.getContenido();
                    dto.imagenUrl = p.getImagenUrl();
                    dto.fecha = p.getFecha();
                    dto.autor = p.getUsuario().getUsername();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Obtener por ID (NUEVO)
    public PublicacionResponseDTO buscarPorId(Long id) {
        Publicacion p = publicacionRepository.findById(id).orElse(null);
        if (p == null) return null;

        PublicacionResponseDTO dto = new PublicacionResponseDTO();
        dto.id = p.getId();
        dto.titulo = p.getTitulo();
        dto.contenido = p.getContenido();
        dto.imagenUrl = p.getImagenUrl();
        dto.fecha = p.getFecha();
        dto.autor = p.getUsuario().getUsername();
        return dto;
    }

    // Actualizar publicación (NUEVO)
    public Object actualizarPublicacion(Long id, CrearPublicacionDTO dto, Usuario usuario) {
        Publicacion p = publicacionRepository.findById(id).orElse(null);
        if (p == null) return "Publicación no encontrada";

        if (!p.getUsuario().getId().equals(usuario.getId()))
            return "No tienes permiso para editar esta publicación";

        p.setTitulo(dto.getTitulo());
        p.setContenido(dto.getContenido());
        p.setImagenUrl(dto.getImagenUrl());

        return publicacionRepository.save(p);
    }

    // Eliminar
    public boolean eliminar(Long id, Usuario usuario) {
        Publicacion p = publicacionRepository.findById(id).orElse(null);
        if (p == null) return false;

        if (!p.getUsuario().getId().equals(usuario.getId())) return false;

        publicacionRepository.delete(p);
        return true;
    }
}
