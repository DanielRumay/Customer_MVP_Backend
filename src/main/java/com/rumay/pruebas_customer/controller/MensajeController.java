package com.rumay.pruebas_customer.controller;

import com.rumay.pruebas_customer.model.Mensaje;
import com.rumay.pruebas_customer.model.entity.MensajeEntity;
import com.rumay.pruebas_customer.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*")
public class MensajeController {

    @Autowired
    private MensajeRepository mensajeRepository;

    // 🔹 WebSocket
    @MessageMapping("/envio")
    @SendTo("/tema/mensajes")
    public Mensaje envio(Mensaje mensaje) {
        mensajeRepository.save(new MensajeEntity(mensaje.nombre(), mensaje.contenido()));
        return new Mensaje(mensaje.nombre(), mensaje.contenido());
    }

    // 🔹 REST: obtener historial
    @GetMapping
    public List<MensajeEntity> listarMensajes() {
        return mensajeRepository.findAll();
    }
}
