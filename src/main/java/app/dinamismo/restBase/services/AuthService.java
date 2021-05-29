/**
 * @author Jeferson Luis dos Passos Silva
 *
 * @since May 17, 2021 7:22:29 PM
 */
package app.dinamismo.restBase.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.repositories.ClienteRepository;
import app.dinamismo.restBase.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	//obs. deveria ser  um servico cliente.
	//@Autowired
	//private ClienteService clienteService;
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPassword;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
		
	public void sendNewPassword(String email) {
		//Cliente cliente = clienteService.findByEmail(email);
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPassWord = newPassWordGerador();
		cliente.setSenha(bCryptPassword.encode(newPassWord));
						 
		//clienteService.update(cliente);
		clienteRepository.save(cliente);
		emailService.sendNewPassWordEmail(cliente, newPassWord);		
	}
	
	private String newPassWordGerador() {
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i]=ramdomChar();
		}
		return new String(vet);
	}
	
	private char ramdomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {//gera um digito
			return (char) (rand.nextInt(10)+48);
		}
		else if ( opt == 0) { //gera letra maiuscula
			return (char) (rand.nextInt(26)+65);
		}
		else {//gera letra minuscula
			return (char) (rand.nextInt(26)+97);
		}
		
	}
	
	
		
	
}
