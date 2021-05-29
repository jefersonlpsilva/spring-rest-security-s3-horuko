/**
 *  @author jefersonlpsilva
 *  @since May 05, 2021
 */

package app.dinamismo.restBase.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import app.dinamismo.restBase.services.EmailService;
import app.dinamismo.restBase.services.MockEmailService;
import app.dinamismo.restBase.services._DBService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	_DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDataBase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
