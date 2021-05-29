package app.dinamismo.restBase.repositories;
/**
 *  @author jefersonlpsilva
 *  @since Apr 29, 2021
 */


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.dinamismo.restBase.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository <Estado, Integer> {
	
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
	

}