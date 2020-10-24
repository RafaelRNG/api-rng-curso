package com.rng.apirng.services;

import java.util.List;
import java.util.Optional;

import com.rng.apirng.services.exception.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rng.apirng.domain.Categoria;
import com.rng.apirng.repositories.CategoriaRepository;
import com.rng.apirng.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> buscarTodos(){
		return categoriaRepository.findAll();
	}

	public Page<Categoria> buscarTodosComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}

	public Categoria buscarPorId(Long id) {

    	Optional<Categoria> categoria = categoriaRepository.findById(id);

    	return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id));
	}

	public void salvar(Categoria categoria){

		categoriaRepository.save(categoria);
	}

	public Categoria alterar(Long id, Categoria novaCategoria) {
		Optional<Categoria> velhaCategoria = categoriaRepository.findById(id);

		novaCategoria.setId(velhaCategoria.get().getId());
		return categoriaRepository.save(novaCategoria);
	}

	public void deletar(Long id){
		try{
			categoriaRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é pissível excluir uma categoria que nao possui Produtos");
		}
	}
}
