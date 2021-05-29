/**
 *  @author jefersonlpsilva
 *  @since Apr 29, 2021
 */
package app.dinamismo.restBase.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import app.dinamismo.restBase.domain.Categoria;
import app.dinamismo.restBase.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

	//gerar consulta sem utilizar @Query
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
	//@Transactional(readOnly=true)	
	//Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
			
}
