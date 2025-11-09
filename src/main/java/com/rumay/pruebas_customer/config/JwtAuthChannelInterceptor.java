package com.rumay.pruebas_customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.rumay.pruebas_customer.service.JwtService;
import com.rumay.pruebas_customer.repository.UsuarioRepository;

@Component
public class JwtAuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if ("CONNECT".equals(accessor.getCommand() + "")) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String username = jwtService.extraerUsername(token);

                var usuario = usuarioRepository.findByUsername(username).orElseThrow();

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, null);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        return message;
    }
}
