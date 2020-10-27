package com.rng.apirng.resources;

import com.rng.apirng.domain.Cliente;
import com.rng.apirng.dto.ClienteDTO;
import com.rng.apirng.services.ClienteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarTodos(){
        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping(path = "/page")
    public ResponseEntity<Page<Cliente>> buscarComPaginacao(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(name = "diretion", defaultValue = "ASC") String direction){

           Page<Cliente> clientes = clienteService.buscarComPaginacao(page, linesPerPage, orderBy, direction);

           return ResponseEntity.ok(clientes);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id){

        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = clienteService.fromDTO(id, clienteDTO);

        clienteService.alterar(id, cliente);

        return ResponseEntity.noContent().build();
    }

//    @PutMapping(path = "/{id}")
//    public ResponseEntity<?> alterar(Long id, Cliente cliente){
//        clienteService.alterar(id, cliente);
//
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
