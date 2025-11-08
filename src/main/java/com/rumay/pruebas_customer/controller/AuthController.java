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
@CrossOrigin(origins = "*")  // AGREGADO: Permitir CORS en el controlador
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {
        try {
            // AGREGADO: Log para depurar
            System.out.println("📝 Intento de registro:");
            System.out.println("   Username: " + request.getUsername());
            System.out.println("   Nombre: " + request.getNombre());
            System.out.println("   Email: " + request.getEmail());

            Usuario usuario = usuarioService.registrarUsuario(
                    request.getUsername(),
                    request.getPassword(),
                    request.getNombre(),
                    request.getEmail()
            );

            String token = jwtService.generarToken(usuario.getUsername(), usuario.getNombre());

            System.out.println("✅ Usuario registrado exitosamente: " + usuario.getUsername());

            return ResponseEntity.ok(new AuthResponse(token, usuario.getNombre(), usuario.getUsername()));
        } catch (RuntimeException e) {
            System.err.println("❌ Error en registro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // AGREGADO: Log para depurar
            System.out.println("🔐 Intento de login:");
            System.out.println("   Username: " + request.getUsername());

            Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(request.getUsername());

            if (usuarioOpt.isEmpty()) {
                System.err.println("❌ Usuario no encontrado: " + request.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
            }

            Usuario usuario = usuarioOpt.get();

            if (!usuarioService.validarPassword(request.getPassword(), usuario.getPassword())) {
                System.err.println("❌ Contraseña incorrecta para: " + request.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
            }

            String token = jwtService.generarToken(usuario.getUsername(), usuario.getNombre());

            System.out.println("✅ Login exitoso: " + usuario.getUsername());

            return ResponseEntity.ok(new AuthResponse(token, usuario.getNombre(), usuario.getUsername()));
        } catch (Exception e) {
            System.err.println("❌ Error en login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extraerUsername(token);
            String nombre = jwtService.extraerNombre(token);

            if (jwtService.validarToken(token, username)) {
                return ResponseEntity.ok(new AuthResponse(token, nombre, username));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }
}