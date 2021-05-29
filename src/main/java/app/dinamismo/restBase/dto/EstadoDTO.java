/**
 *  @author jefersonlpsilva
 *  @since May 26, 2021
 */
package app.dinamismo.restBase.dto;

import java.io.Serializable;

import app.dinamismo.restBase.domain.Estado;


public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 3374111799999L;
	
	private Integer id;
	private String nome;		
		
	public EstadoDTO() {
		
	}
	
	public EstadoDTO(Estado obj) {		
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
