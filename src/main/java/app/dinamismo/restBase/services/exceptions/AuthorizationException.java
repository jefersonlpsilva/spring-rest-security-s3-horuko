/**
 *  @author jefersonlpsilva
 *  @since May 16, 2021
 */
package app.dinamismo.restBase.services.exceptions;


public class AuthorizationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String message) {
		super(message);
	}
	
	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
