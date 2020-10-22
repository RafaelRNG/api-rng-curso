package com.rng.apirng.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rng.apirng.domain.Categoria;
import com.rng.apirng.repositories.CategoriaRepository;
import com.rng.apirng.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria buscarPorId(Long id) {

    	Optional<Categoria> categoria = categoriaRepository.findById(id);

    	return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! Id: " + id));
	}

	public void salvar(Categoria categoria){

		categoriaRepository.save(categoria);
	}

	public Categoria alterar(Long id, Categoria novaCategoria){
		Optional<Categoria> velhaCategoria = categoriaRepository.findById(id);

		novaCategoria.setId(velhaCategoria.get().getId());
		return categoriaRepository.save(novaCategoria);
	}
}
