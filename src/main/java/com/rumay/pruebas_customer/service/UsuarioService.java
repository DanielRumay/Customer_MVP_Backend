package com.rumay.pruebas_customer.service;

import com.rumay.pruebas_customer.model.Usuario;
import com.rumay.pruebas_customer.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // Registrar usuario
    public Usuario registrarUsuario(String username, String password, String nombre, String email) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El username ya está en uso");
        }

        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setNombre(nombre);
        u.setEmail(email);

        return usuarioRepository.save(u);
    }

    // Buscar por username
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Validar contraseña
    public boolean validarPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Obtener usuario a partir del token JWT
    public Usuario getUserFromToken(String token) {

        // Quitar "Bearer " si viene en el Header
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String username = jwtService.extraerUsername(token);

        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el token"));
    }
}
