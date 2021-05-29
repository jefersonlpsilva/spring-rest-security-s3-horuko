/**
 *  @author jefersonlpsilva
 *  @since May 5, 2021
 */
package app.dinamismo.restBase.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
import app.dinamismo.restBase.domain.PagamentoComBoleto;


@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, java.util.Date date) {
		Calendar cal =  Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH,7);
	}


}
