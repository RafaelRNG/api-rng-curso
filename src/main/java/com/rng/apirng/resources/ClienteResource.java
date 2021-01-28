package com.rng.apirng.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rng.apirng.domain.Cliente;
import com.rng.apirng.dto.ClienteDTO;
import com.rng.apirng.dto.ClienteNewDTO;
import com.rng.apirng.services.ClienteService;

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

    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
        Cliente cliente = clienteService.fromDTO(clienteNewDTO);
        clienteService.salvar(cliente);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri()).build();
    }

    @PostMapping(path = "/picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam(name = "file") MultipartFile multipartFile) {
        URI uri = clienteService.uploadProfilePicture(multipartFile);

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = clienteService.fromDTO(id, clienteDTO);

        clienteService.alterar(id, cliente);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
