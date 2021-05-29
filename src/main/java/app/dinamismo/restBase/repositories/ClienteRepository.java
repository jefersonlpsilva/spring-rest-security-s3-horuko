/**
 *  @author jefersonlpsilva
 *  @since Apr 30, 2021
 */
package app.dinamismo.restBase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.dinamismo.restBase.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Transactional (readOnly = true)
	Cliente findByEmail(String email); 

}
