package com.rng.apirng.resources;

import com.rng.apirng.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rng.apirng.domain.Categoria;
import com.rng.apirng.services.CategoriaService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> buscarTodos(){
		List<Categoria> categorias = categoriaService.buscarTodos();
		List<CategoriaDTO> categoriaDTOS = categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());

		return ResponseEntity.ok(categoriaDTOS);
	}

	@GetMapping(path = "/page")
	public ResponseEntity<Page<CategoriaDTO>> buscarTodosComPaginacao(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			){

		Page<Categoria> categorias = categoriaService.buscarTodosComPaginacao(page, linesPerPage, orderBy,direction);
		Page<CategoriaDTO> categoriaDTOS = categorias.map(categoria -> new CategoriaDTO(categoria));

		return ResponseEntity.ok(categoriaDTOS);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
		Categoria categoria = categoriaService.buscarPorId(id);
		CategoriaDTO categoriaDTO = new CategoriaDTO(categoria);

		return ResponseEntity.ok(categoriaDTO);
	}

	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);

		categoriaService.salvar(categoria);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri()).build();
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoriaService.alterar(id, categoria);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){

		categoriaService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
