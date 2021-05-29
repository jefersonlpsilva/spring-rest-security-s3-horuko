/**
 *  @author jefersonlpsilva
 *  @since Apr 29, 2021
 */
package app.dinamismo.restBase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.dinamismo.restBase.domain.Cidade;

@Repository
public interface CidadeRespository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
			
}
