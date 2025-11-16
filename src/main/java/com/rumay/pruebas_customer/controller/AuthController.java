package com.rumay.pruebas_customer.controller;

import com.rumay.pruebas_customer.model.*;
import com.rumay.pruebas_customer.service.JwtService;
import com.rumay.pruebas_customer.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(
                    request.getUsername(),
                    request.getPassword(),
                    request.getNombre(),
                    request.getEmail()
            );

            String token = jwtService.generarToken(usuario.getUsername(), usuario.getNombre());

            return ResponseEntity.ok(new AuthResponse(token, usuario.getNombre(), usuario.getUsername()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(request.getUsername());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuarioService.validarPassword(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getNombre());

        return ResponseEntity.ok(new AuthResponse(token, usuario.getNombre(), usuario.getUsername()));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extraerUsername(token);
            String nombre = jwtService.extraerNombre(token);

            if (jwtService.validarToken(token, username)) {
                return ResponseEntity.ok(new AuthResponse(token, nombre, username));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }
}
