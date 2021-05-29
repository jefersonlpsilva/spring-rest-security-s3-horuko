package app.dinamismo.restBase.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import app.dinamismo.restBase.domain.Cidade;
import app.dinamismo.restBase.domain.Cliente;
import app.dinamismo.restBase.domain.Endereco;
import app.dinamismo.restBase.domain.enums.Perfil;
import app.dinamismo.restBase.domain.enums.TipoCliente;
import app.dinamismo.restBase.dto.ClienteDTO;
import app.dinamismo.restBase.dto.ClienteNewDTO;
import app.dinamismo.restBase.repositories.ClienteRepository;
import app.dinamismo.restBase.repositories.EnderecoRepository;
import app.dinamismo.restBase.security.UserSS;
import app.dinamismo.restBase.services.exceptions.AuthorizationException;
import app.dinamismo.restBase.services.exceptions.DataIntegrityException;
import app.dinamismo.restBase.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository repoEndereco; 
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPassword;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")	
	private String prefix;
	
	@Value("${img.profile.size}")	
	private Integer size;
	
	public Cliente find(Integer id) {		
		UserSS user = UserService.authenticated();		
		if (user==null) {		
			throw new AuthorizationException("Acesso negado");
		}else if ((!user.hasRole(Perfil.ADMIN)) && (!id.equals(user.getId()))) {  
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> Obj = repo.findById(id);		
		return Obj.orElseThrow(() ->
		        new ObjectNotFoundException("Objeto não encontrado! Id:"+id+" Tipo:"+Cliente.class.getName())		        
		);
	}
		
	public List<Cliente> findAll(){		
		return repo.findAll();
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj); 
		repoEndereco.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {		
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente com pedidos relacionados");
		}		
	}
	
	//http://localhost:8081/categorias/page?linesPerPage=4&page=0&orderBy=nome&direction=ASC
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
	  PageRequest pageRequest = PageRequest.of(
					  page, 
					  linesPerPage, 
					  Direction.valueOf(direction),
					  orderBy);	  
	  return repo.findAll(pageRequest);		
	}
	
	public Cliente fromDto(ClienteDTO objDto) {
	   return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null,objDto.getNome(),objDto.getEmail(), objDto.getCpfCnpj(), TipoCliente.toEnum(objDto.getTipoCliente()),  bCryptPassword.encode(objDto.getSenha()) );
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end=  new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null)
			cli.getTelefones().add(objDto.getTelefone2());
		if (objDto.getTelefone3()!=null) 
			cli.getTelefones().add(objDto.getTelefone3());		   		   
		return cli; 		   		   
	}
	
	/**
	public Cliente findByEmail(String email) {
		return repo.findByEmail(email);
	}
	**/
	
	public Cliente findByEmail(String email) {
		
		UserSS user = UserService.authenticated();			
		
		if ((user==null) || (!user.hasRole(Perfil.ADMIN)) && (!email.equals(user.getUsername()))) {  
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente obj = repo.findByEmail(email); 
		
		if (obj ==null) {
			throw new ObjectNotFoundException("Objeto não encontrado! id: "+user.getId()+", Tipo: "+Cliente.class.getName());
		}
		
		return obj;		
	}
	
	
	
	
	
		
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());		
	}
	
	public URI uploadProfilePictures(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();		
		if (user==null) {		
			throw new AuthorizationException("Acesso negado");
		}
			
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");				
	}

	
	
	

}
