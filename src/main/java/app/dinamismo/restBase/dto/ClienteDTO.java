/**
 *  @author jefersonlpsilva
 *  @since May 4, 2021
 */
package app.dinamismo.restBase.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.services.validation.ClienteUpDate;

@ClienteUpDate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 3374111799999L;
	
	private Integer id;
	@NotEmpty(message="Preencimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preencimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	@NotEmpty(message="Preencimento obrigatório")
	private String senha;

	public ClienteDTO() {	
	}

	public ClienteDTO(Cliente obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
