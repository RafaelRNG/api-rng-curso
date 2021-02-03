package com.rng.apirng.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rng.apirng.domain.Estado;
import com.rng.apirng.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAllByOrderByNome(){
		
		return estadoRepository.findAllByOrderByNome();
	}
}