/**
 *  @author jefersonlpsilva
 *  @since May 5, 2021
 */
package app.dinamismo.restBase.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import app.dinamismo.restBase.services.EmailService;
import app.dinamismo.restBase.services.MockEmailService;
import app.dinamismo.restBase.services.SmtpEmailService;
import app.dinamismo.restBase.services._DBService;

@Configuration
@Profile("prod")
public class ProdConfig {
	
	@Autowired
	_DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if (!"create".equals(strategy)) {
			return false;
		}
		
		dbService.instantiateTestDataBase();		
		return true;
	}
		
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
	
	

}
