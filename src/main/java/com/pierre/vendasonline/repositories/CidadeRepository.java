package com.pierre.vendasonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierre.vendasonline.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
