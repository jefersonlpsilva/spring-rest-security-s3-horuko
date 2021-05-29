/**
 * @author Jeferson Luis dos Passos Silva
 *
 * @since May 27, 2021 12:09:16 AM
 */
package app.dinamismo.restBase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dinamismo.restBase.domain.Cidade;
import app.dinamismo.restBase.repositories.CidadeRespository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRespository repository;
	
	public List<Cidade> findByEstado(Integer estadoId){					
		return repository.findCidades(estadoId);
	}

}
