package com.rng.apirng.services;

import com.rng.apirng.domain.Pedido;
import com.rng.apirng.repositories.PedidoRepository;
import com.rng.apirng.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarPorId(Long id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado, ID: " + id));
    }
}
