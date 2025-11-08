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

    public Usuario registrarUsuario(String username, String password, String nombre, String email) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El usuario ya existe");
        }

        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setNombre(nombre);
        usuario.setEmail(email);

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean validarPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}