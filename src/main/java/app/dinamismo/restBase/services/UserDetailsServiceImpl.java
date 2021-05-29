/**
 * 
 */
package app.dinamismo.restBase.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.domain.enums.Perfil;
import app.dinamismo.restBase.repositories.ClienteRepository;
import app.dinamismo.restBase.security.UserSS;

/**
 * @author jefersonlpsilva
 * @since 15/05/2021
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
		Cliente cliente = clienteRepo.findByEmail(email);
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}		
		return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());		
	}

}
