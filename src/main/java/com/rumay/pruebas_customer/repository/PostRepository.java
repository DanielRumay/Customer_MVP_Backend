package com.rumay.pruebas_customer.repository;

import com.rumay.pruebas_customer.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
