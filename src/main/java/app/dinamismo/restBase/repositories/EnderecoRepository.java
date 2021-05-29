/**
 *  @author jefersonlpsilva
 *  @since Apr 30, 2021
 */
package app.dinamismo.restBase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.dinamismo.restBase.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
