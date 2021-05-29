/**
 *  @author jefersonlpsilva
 *  @since May , 2021
 */
package app.dinamismo.restBase.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.domain.Pedido;

public abstract class AbstractEmailService  implements EmailService {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
		
	@Value("${default.sender}")
	private String sender;
		
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage simplesMailMessage = preparaSimpleMailMessage(obj);    
		sendEmail(simplesMailMessage);
		   	
	}
		
	protected SimpleMailMessage preparaSimpleMailMessage(Pedido obj) {
		SimpleMailMessage simplesMailMessage = new SimpleMailMessage();
		simplesMailMessage.setTo(obj.getCliente().getEmail());
		simplesMailMessage.setFrom(sender);
		simplesMailMessage.setSubject("Pedido confirmado: "+obj.getId());
		simplesMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simplesMailMessage.setText(obj.toString());
		return simplesMailMessage;
	}
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido.html", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mimeMessage = preparaMimeMessageFromPedido(obj);
			sendHtmlEmail(mimeMessage);
		}catch(MessagingException e) {
			sendOrderConfirmationEmail(obj);
			e.printStackTrace();
		}
	}
		
	protected MimeMessage preparaMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(obj.getCliente().getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("Pedido confirmado: "+obj.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePedido(obj),true);
		return mimeMessage;
		
	}
	
	@Override
	public void sendNewPassWordEmail(Cliente cliente, String newPassWord) {
		SimpleMailMessage simplesMailMessage = preparaNewPassWordEmail(cliente,newPassWord);    
		sendEmail(simplesMailMessage);		
	}

	protected SimpleMailMessage preparaNewPassWordEmail(Cliente cliente, String newPassWord) {
		SimpleMailMessage simplesMailMessage = new SimpleMailMessage();
		simplesMailMessage.setTo(cliente.getEmail());
		simplesMailMessage.setFrom(sender);
		simplesMailMessage.setSubject("Solicitação de nova senha");
		simplesMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simplesMailMessage.setText("Nova senha: "+newPassWord);
		return simplesMailMessage;
	}
	
	
}
