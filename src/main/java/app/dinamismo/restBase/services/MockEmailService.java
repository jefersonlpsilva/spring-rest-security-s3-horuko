/**
 *  @author jefersonlpsilva
 *  @since May , 2021
 */
package app.dinamismo.restBase.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
	
	private static Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		LOG.info(msg.toString());
		LOG.info("Enviado email");
	}
	
	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email...");
		LOG.info(msg.toString());		
		LOG.info("Enviado email");		
	}	
}
