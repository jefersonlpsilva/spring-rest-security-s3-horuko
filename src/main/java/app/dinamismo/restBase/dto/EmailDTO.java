/**
 *  @author jefersonlpsilva
 *  @since May 4, 2021
 */
package app.dinamismo.restBase.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


public class EmailDTO implements Serializable {	
	private static final long serialVersionUID = 3374111799999L;

		
	@NotEmpty(message="Preencimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	public EmailDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}		
}