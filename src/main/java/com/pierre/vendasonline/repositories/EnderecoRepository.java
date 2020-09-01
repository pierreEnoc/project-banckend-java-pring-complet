package com.pierre.vendasonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierre.vendasonline.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
