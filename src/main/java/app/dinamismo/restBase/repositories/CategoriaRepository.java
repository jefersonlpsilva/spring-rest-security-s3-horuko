/**
 *  @author jefersonlpsilva
 *  @since Apr 29, 2021
 */
package app.dinamismo.restBase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.dinamismo.restBase.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>  {

}
