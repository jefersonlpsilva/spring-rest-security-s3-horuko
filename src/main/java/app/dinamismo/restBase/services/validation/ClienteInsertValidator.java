package app.dinamismo.restBase.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.domain.enums.TipoCliente;
import app.dinamismo.restBase.dto.ClienteNewDTO;
import app.dinamismo.restBase.repositories.ClienteRepository;
import app.dinamismo.restBase.resources.exception.FieldMessage;
import app.dinamismo.restBase.services.validation.util.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public void initialize(ClienteInsert ann){	
	}		
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA) &&
				!BR.isValidCPF(objDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj","CPF inválido"));		
		}
		
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA) &&
				!BR.isValidCNPJ(objDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj","CNPJ inválido"));		
		}
				
		Cliente clienteJáExiste = clienteRepo.findByEmail(objDto.getEmail());
		if (clienteJáExiste != null)
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
