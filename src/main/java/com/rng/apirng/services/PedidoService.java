package com.rng.apirng.services;

import com.rng.apirng.domain.ItemPedido;
import com.rng.apirng.domain.PagamentoComBoleto;
import com.rng.apirng.domain.Pedido;
import com.rng.apirng.domain.enums.EstadoPagamento;
import com.rng.apirng.repositories.ItemPedidoRepository;
import com.rng.apirng.repositories.PagamentoRepository;
import com.rng.apirng.repositories.PedidoRepository;
import com.rng.apirng.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    public Pedido buscarPorId(Long id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado, ID: " + id));
    }

    public Pedido salvar(Pedido pedido){

        pedido.setInstant(new Date());
        pedido.setCliente(clienteService.buscarPorId(pedido.getCliente().getId()));
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if(pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstant());
        }

        pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for(ItemPedido itemPedido : pedido.getItens()){
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoService.buscarPorId(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());

        System.out.println(pedido);
        return pedido;
    }
}