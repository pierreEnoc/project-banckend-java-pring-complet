package com.pierre.vendasonline.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pierre.vendasonline.domain.Cliente;
import com.pierre.vendasonline.domain.ItemPedido;
import com.pierre.vendasonline.domain.PagamentoComBoleto;
import com.pierre.vendasonline.domain.Pedido;
import com.pierre.vendasonline.domain.enums.EstadoPagamento;
import com.pierre.vendasonline.repositories.ClienteRepository;
import com.pierre.vendasonline.repositories.ItemPedidoRepository;
import com.pierre.vendasonline.repositories.PagamentoRepository;
import com.pierre.vendasonline.repositories.PedidoRepository;
import com.pierre.vendasonline.security.UserSS;
import com.pierre.vendasonline.services.exceptions.AuthorizationException;
import com.pierre.vendasonline.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	

	@Autowired
	private BoletoService boletoService;
	

	@Autowired
	private PagamentoRepository  pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository  itemPedidoRepository;
	
	@Autowired
	private ProdutoService  produtoService;
	

	@Autowired
	private ClienteService clienteService;
	
	//@org.springframework.beans.factory.annotation.Autowired(required=true)
	@Autowired
	private EmailService emailService;

	
	public Pedido find(Integer id) {
		Optional<Pedido>  obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));;
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		 if(user == null) {
			 throw new AuthorizationException("Acesso negado");
		 }
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);	
	}

}
