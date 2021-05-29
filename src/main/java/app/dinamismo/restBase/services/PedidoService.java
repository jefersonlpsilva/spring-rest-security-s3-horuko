/**
 *  @author jefersonlpsilva
 *  @since May , 2021
 */


package app.dinamismo.restBase.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dinamismo.restBase.domain.Categoria;
import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.domain.ItemPedido;
import app.dinamismo.restBase.domain.PagamentoComBoleto;
import app.dinamismo.restBase.domain.Pedido;
import app.dinamismo.restBase.domain.enums.EstadoPagamento;
import app.dinamismo.restBase.domain.enums.Perfil;
import app.dinamismo.restBase.repositories.ItemPedidoRepository;
import app.dinamismo.restBase.repositories.PagamentoRepository;
import app.dinamismo.restBase.repositories.PedidoRepository;
import app.dinamismo.restBase.security.UserSS;
import app.dinamismo.restBase.services.exceptions.AuthorizationException;
import app.dinamismo.restBase.services.exceptions.ObjectNotFoundException;

//Ficou quebrada a regra de que ..somente servicos salvam dados do seu 
//repositio
//Necessario trocar todos repositorios por service.


@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
		
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;  
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido find(Integer id) {										
		Optional<Pedido> obj = repository.findById(id);		
		if (obj.isPresent()) {       
			UserSS user = UserService.authenticated();
			if (user == null) {
				throw new AuthorizationException("Acesso negado");
			}else if ((!user.hasRole(Perfil.ADMIN)) 
				   && (!obj.get().getCliente().getId().equals(user.getId()))){  
				throw new AuthorizationException("Acesso negado");
			}
		}
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! Id:"+id+" Tipo:"+Pedido.class.getName()));
	}

	
	//Ficou quebrada a regra de que ..somente servicos salvam dados do seu 
	//repositio
	//Necessario trocar todos repositorios por service.
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);		
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento()  instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}				
		itemPedidoRepository.saveAll(obj.getItens());
		//System.out.print(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){		
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}		
		PageRequest pageRequest = PageRequest.of(
				  page, 
				  linesPerPage, 
				  Direction.valueOf(direction),
				  orderBy);		
		Cliente cliente = clienteService.find(user.getId());
		
		return repository.findByCliente(cliente, pageRequest);					
	}
	
	
}