/**
 * @author Jeferson Luis dos Passos Silva
 *
 * @since May 16, 2021 12:38:09 AM
 */
package app.dinamismo.restBase.services;

import org.springframework.security.core.context.SecurityContextHolder;

import app.dinamismo.restBase.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS)SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
		}
		catch(Exception e) {
			return null;
		}
	}
}
