package com.rumay.pruebas_customer.controller;

import com.rumay.pruebas_customer.model.Mensaje;
import com.rumay.pruebas_customer.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*")
public class MensajeController {

    @Autowired
    private MensajeRepository mensajeRepository;

    // Chat en tiempo real (STOMP)
    @MessageMapping("/envio")
    @SendTo("/tema/mensajes")
    public Mensaje envio(Mensaje mensaje) {
        Mensaje guardado = new Mensaje(mensaje.getNombre(), mensaje.getContenido());
        mensajeRepository.save(guardado);
        return guardado;
    }

    // Historial de mensajes (para cuando un usuario entra)
    @GetMapping
    public List<Mensaje> obtenerMensajes() {
        return mensajeRepository.findAll();
    }
}
