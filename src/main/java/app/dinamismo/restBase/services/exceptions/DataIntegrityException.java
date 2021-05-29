/**
 *  @author jefersonlpsilva
 *  @since May 3, 2021
 */
package app.dinamismo.restBase.services.exceptions;


public class DataIntegrityException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String message) {
		super(message);
	}
	
	public DataIntegrityException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
