package com.pierre.vendasonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierre.vendasonline.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
