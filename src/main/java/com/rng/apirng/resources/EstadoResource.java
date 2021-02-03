package com.rng.apirng.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rng.apirng.domain.Cidade;
import com.rng.apirng.domain.Estado;
import com.rng.apirng.dto.CidadeDTO;
import com.rng.apirng.dto.EstadoDTO;
import com.rng.apirng.services.CidadeService;
import com.rng.apirng.services.EstadoService;

@RestController
@RequestMapping(path = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAllByOrderByNome() {

		List<Estado> estados = estadoService.findAllByOrderByNome();
		
		List<EstadoDTO> estadosDTO = estados
				.stream()
				.map(estado -> new EstadoDTO(estado))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(estadosDTO);
	}
	
	@GetMapping(path ="/{estado_id}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable(name = "estado_id") Long estadoId){
		
		List<Cidade> cidades = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidadesDTO = cidades
				.stream()
				.map(cidade -> new CidadeDTO(cidade))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(cidadesDTO);
	}
}