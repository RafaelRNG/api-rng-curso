package com.rng.apirng.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rng.apirng.domain.Categoria;
import com.rng.apirng.services.CategoriaService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {

		return ResponseEntity.ok(categoriaService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Categoria categoria){
		if(categoria.getNome() == null){
			return ResponseEntity.badRequest().build();
		}

		categoriaService.salvar(categoria);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri()).build();
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody Categoria categoria){
		categoriaService.alterar(id, categoria);

		return ResponseEntity.noContent().build();
	}
}
