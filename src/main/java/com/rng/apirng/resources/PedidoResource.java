package com.rng.apirng.resources;

import com.rng.apirng.domain.Pedido;
import com.rng.apirng.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id){

        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> Salvar(@RequestBody Pedido pedido){
        pedidoService.salvar(pedido);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri()).build();
    }

    @GetMapping
    public ResponseEntity<Page<Pedido>> pagination(
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(name = "direction", defaultValue = "DESC")String direction,
            @RequestParam(name = "orderBy", defaultValue = "instant") String orderBy
    ){
        return ResponseEntity.ok(pedidoService.paginagion(page, linesPerPage, orderBy, direction));
    }
}