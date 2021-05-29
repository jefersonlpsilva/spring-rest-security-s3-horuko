/**
 *  @author jefersonlpsilva
 *  @since May 10, 2021
 */
package app.dinamismo.restBase.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);

	void sendNewPassWordEmail(Cliente cliente, String newPassWord);

}
