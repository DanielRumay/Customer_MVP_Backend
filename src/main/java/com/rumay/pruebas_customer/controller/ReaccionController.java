package com.rumay.pruebas_customer.controller;

import com.rumay.pruebas_customer.model.Usuario;
import com.rumay.pruebas_customer.model.dto.ReaccionResponseDTO;
import com.rumay.pruebas_customer.service.ReaccionService;
import com.rumay.pruebas_customer.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reacciones")
@CrossOrigin
public class ReaccionController {

    private final ReaccionService reaccionService;
    private final UsuarioService usuarioService;

    public ReaccionController(ReaccionService rService, UsuarioService uService) {
        this.reaccionService = rService;
        this.usuarioService = uService;
    }

    @PostMapping("/{id}/like")
    public ReaccionResponseDTO toggleLike(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        Usuario usuario = usuarioService.getUserFromToken(token);
        return reaccionService.toggleLike(id, usuario);
    }

    @PostMapping("/{id}/dislike")
    public ReaccionResponseDTO toggleDislike(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        Usuario usuario = usuarioService.getUserFromToken(token);
        return reaccionService.toggleDislike(id, usuario);
    }
}
