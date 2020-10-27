package com.rng.apirng.services;

import com.rng.apirng.domain.Cliente;
import com.rng.apirng.dto.ClienteDTO;
import com.rng.apirng.repositories.ClienteRepository;
import com.rng.apirng.services.exception.DataIntegrityException;
import com.rng.apirng.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> buscarTodos(){
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = clientes.stream().map(cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());

        return clientesDTO;
    }

//    public List<Cliente> buscarTodos(){
//        return clienteRepository.findAll();
//    }

    public Page<Cliente> buscarComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        return clienteRepository.findAll(pageRequest);
    }

    public Cliente buscarPorId(Long id){

        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não enconstrado! ID: " + id));
    }

    public Cliente alterar(Long id, Cliente novoCliente){
        Optional<Cliente> velhoCliente = clienteRepository.findById(id);

        novoCliente.setId(velhoCliente.get().getId());
        clienteRepository.save(novoCliente);
        return novoCliente;
    }

    public void deletar(Long id) {
        try{
            clienteRepository.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é pissível excluir porque há entidades relacionadas");
        }
    }

    public Cliente fromDTO(Long id, ClienteDTO clienteDTO){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), cliente.get().getCpfOuCnpj(), cliente.get().getTipoCliente());
    }
}
