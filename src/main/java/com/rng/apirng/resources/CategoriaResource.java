package com.rng.apirng.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rng.apirng.domain.Categoria;
import com.rng.apirng.dto.CategoriaDTO;
import com.rng.apirng.services.CategoriaService;

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

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);

		categoriaService.salvar(categoria);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri()).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoriaService.alterar(id, categoria);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){

		categoriaService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
