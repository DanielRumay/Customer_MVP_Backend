package com.rumay.pruebas_customer.repository;

import com.rumay.pruebas_customer.model.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeEntity, Long> {
}
