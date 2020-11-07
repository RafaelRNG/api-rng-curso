package com.rng.apirng.resources;

import com.rng.apirng.domain.Produto;
import com.rng.apirng.dto.ProdutoDTO;
import com.rng.apirng.resources.utils.URL;
import com.rng.apirng.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> search(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){

        String nomeDecoded = URL.decodeParam(nome);

        List<Long> ids = URL.decodeLongList(categorias);
        Page<Produto> produtos = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> produtoDTOS = produtos.map(produto -> new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco()));

        return ResponseEntity.ok(produtoDTOS);
    }
}
