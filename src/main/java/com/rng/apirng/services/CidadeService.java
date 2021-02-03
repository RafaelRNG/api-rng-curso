package com.rng.apirng.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rng.apirng.domain.Cidade;
import com.rng.apirng.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado(Long id) {
		return cidadeRepository.findCidades(id);
	}
}