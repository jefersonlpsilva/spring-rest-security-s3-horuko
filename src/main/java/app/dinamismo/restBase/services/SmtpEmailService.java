/**
 *  @author jefersonlpsilva
 *  @since May ,10 2021
 */

package app.dinamismo.restBase.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {		
		LOG.info("Enviando email...sem html");
		LOG.info(msg.toString());
		mailSender.send(msg);
		LOG.info("Enviado email");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email...");
		LOG.info(msg.toString());
		javaMailSender.send(msg);
		LOG.info("Enviado email");		
	}	
}
