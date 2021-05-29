package app.dinamismo.restBase.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dinamismo.restBase.domain.Cidade;
import app.dinamismo.restBase.domain.Estado;
import app.dinamismo.restBase.dto.CidadeDTO;
import app.dinamismo.restBase.dto.EstadoDTO;
import app.dinamismo.restBase.services.CidadeService;
import app.dinamismo.restBase.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
    private static final Logger LOG = LoggerFactory.getLogger(EstadoResource.class);
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	private ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> estados = estadoService.findAll();
		List<EstadoDTO> estadosDTO = estados.stream().map(obj-> new EstadoDTO(obj)).collect(Collectors.toList());		  
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	private ResponseEntity<List<CidadeDTO>> findCidade(@PathVariable Integer estadoId) {
		List<Cidade> cidades = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidadesDTO = cidades.stream().map(obj-> new CidadeDTO(obj)).collect(Collectors.toList());		  
		return ResponseEntity.ok().body(cidadesDTO);
	}
}
