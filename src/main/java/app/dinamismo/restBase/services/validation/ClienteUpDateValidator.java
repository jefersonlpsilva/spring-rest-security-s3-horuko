package app.dinamismo.restBase.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.domain.enums.TipoCliente;
import app.dinamismo.restBase.dto.ClienteDTO;
import app.dinamismo.restBase.dto.ClienteNewDTO;
import app.dinamismo.restBase.repositories.ClienteRepository;
import app.dinamismo.restBase.resources.exception.FieldMessage;
import app.dinamismo.restBase.services.validation.util.BR;

public class ClienteUpDateValidator implements ConstraintValidator<ClienteUpDate, ClienteDTO> {
	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public void initialize(ClienteUpDate ann){	
	}		
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String,String> map = (Map<String,String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
				
		List<FieldMessage> list = new ArrayList<>();
									
		Cliente clienteEncontrado = clienteRepo.findByEmail(objDto.getEmail());
		if ((clienteEncontrado != null) && (!clienteEncontrado.getId().equals(uriId))) 
			list.add(new FieldMessage("email","Email já existente"));
					
		// inclua os testes aqui, inserindo erros na lista		
		for(FieldMessage e: list ){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode( e.getFieldName() )
			.addConstraintViolation();
		}
		return list.isEmpty();
	}	

}
