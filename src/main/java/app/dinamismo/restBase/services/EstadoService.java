/**
 * @author Jeferson Luis dos Passos Silva
 *
 * @since May 27, 2021 12:09:16 AM
 */
package app.dinamismo.restBase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dinamismo.restBase.domain.Estado;
import app.dinamismo.restBase.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	public List<Estado> findAll(){					
		return repository.findAllByOrderByNome();
	}

}
