package com.rumay.pruebas_customer.repository;

import com.rumay.pruebas_customer.model.Reaccion;
import com.rumay.pruebas_customer.model.Usuario;
import com.rumay.pruebas_customer.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaccionRepository extends JpaRepository<Reaccion, Long> {

    Optional<Reaccion> findByUsuarioAndPublicacion(Usuario usuario, Publicacion publicacion);
}
