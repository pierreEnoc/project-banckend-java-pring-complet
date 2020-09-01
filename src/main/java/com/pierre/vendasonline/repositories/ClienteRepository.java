package com.pierre.vendasonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierre.vendasonline.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
