package com.rumay.pruebas_customer.repository;

import com.rumay.pruebas_customer.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
}
