package com.rumay.pruebas_customer.controller;

import com.rumay.pruebas_customer.model.Usuario;
import com.rumay.pruebas_customer.model.dto.CrearPublicacionDTO;
import com.rumay.pruebas_customer.service.PublicacionService;
import com.rumay.pruebas_customer.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin
public class PublicacionController {

    private final PublicacionService publicacionService;
    private final UsuarioService usuarioService;

    public PublicacionController(PublicacionService pService, UsuarioService uService) {
        this.publicacionService = pService;
        this.usuarioService = uService;
    }

    // Crear publicación
    @PostMapping
    public Object crear(@RequestBody CrearPublicacionDTO dto, @RequestHeader("Authorization") String token) {
        Usuario usuario = usuarioService.getUserFromToken(token);
        return publicacionService.crearPublicacion(dto, usuario);
    }

    // Listar publicaciones públicas
    @GetMapping
    public List<?> listar() {
        return publicacionService.listarPublico();
    }

    // Obtener publicación por ID (FALTANTE)
    @GetMapping("/{id}")
    public Object obtenerPorId(@PathVariable Long id) {
        return publicacionService.buscarPorId(id);
    }

    // Actualizar publicación (FALTANTE)
    @PutMapping("/{id}")
    public Object actualizar(
            @PathVariable Long id,
            @RequestBody CrearPublicacionDTO dto,
            @RequestHeader("Authorization") String token) {

        Usuario usuario = usuarioService.getUserFromToken(token);
        return publicacionService.actualizarPublicacion(id, dto, usuario);
    }

    // Eliminar publicación
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        Usuario usuario = usuarioService.getUserFromToken(token);

        boolean ok = publicacionService.eliminar(id, usuario);

        return ok ? "Publicación eliminada"
                : "No tienes permiso para eliminar esta publicación";
    }
}
