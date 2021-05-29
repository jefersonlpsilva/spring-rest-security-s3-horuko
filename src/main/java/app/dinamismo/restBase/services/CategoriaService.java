/**
 *  @author jefersonlpsilva
 *  @since Apr 29, 2021
 */
package app.dinamismo.restBase.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import app.dinamismo.restBase.domain.Categoria;
import app.dinamismo.restBase.dto.CategoriaDTO;
import app.dinamismo.restBase.repositories.CategoriaRepository;
import app.dinamismo.restBase.services.exceptions.DataIntegrityException;
import app.dinamismo.restBase.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	//Incializada pelo spring por inject/
	//dependencia de controle
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado! Id:"+id+" Tipo:"+Categoria.class.getName())
				);				
	}
	
	public List<Categoria> findAll(){		
		return repo.findAll();
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {		
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}		
	}
	
	//http://localhost:8081/categorias/page?linesPerPage=4&page=0&orderBy=nome&direction=ASC
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
	  PageRequest pageRequest = PageRequest.of(
					  page, 
					  linesPerPage, 
					  Direction.valueOf(direction),
					  orderBy);	  
	  return repo.findAll(pageRequest);		
	}
	
	public Categoria fromDto(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(),objDto.getNome());
	}
	
	
	
	
}
