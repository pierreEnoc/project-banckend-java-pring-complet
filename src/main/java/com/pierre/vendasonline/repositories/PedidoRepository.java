package com.pierre.vendasonline.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pierre.vendasonline.domain.Cliente;
import com.pierre.vendasonline.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	@Transactional(readOnly= true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

}
