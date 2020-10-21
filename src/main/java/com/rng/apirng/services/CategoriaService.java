package com.rng.apirng.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
}
