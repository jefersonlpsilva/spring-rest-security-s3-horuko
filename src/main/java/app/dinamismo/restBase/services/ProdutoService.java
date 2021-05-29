/**
 *  @author jefersonlpsilva
 *  @since May , 2021
 */
package app.dinamismo.restBase.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import app.dinamismo.restBase.domain.Categoria;
import app.dinamismo.restBase.domain.Produto;
import app.dinamismo.restBase.repositories.CategoriaRepository;
import app.dinamismo.restBase.repositories.ProdutoRepository;
import app.dinamismo.restBase.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	
	public Produto find(Integer id) {				
		Optional<Produto> obj = repo.findById(id);		
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! Id:"+id+" Tipo:"+Produto.class.getName()
				));
	}
		
	
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);	
	}
	
	
	
				
}