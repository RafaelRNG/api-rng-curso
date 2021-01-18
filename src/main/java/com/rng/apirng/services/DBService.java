package com.rng.apirng.services;

import com.rng.apirng.domain.*;
import com.rng.apirng.domain.enums.EstadoPagamento;
import com.rng.apirng.domain.enums.Perfil;
import com.rng.apirng.domain.enums.TipoCliente;
import com.rng.apirng.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instantiateTestDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null, "Informatica");
        Categoria cat2 = new Categoria(null, "Escritorio");
        Categoria cat3 = new Categoria(null, "Casa");
        Categoria cat4 = new Categoria(null, "Eletronica");
        Categoria cat5 = new Categoria(null, "Mesa e banho");
        Categoria cat6 = new Categoria(null, "Papelaria");
        Categoria cat7 = new Categoria(null, "Farmacia");

        Produto p1 = new Produto(null, "computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        Produto p4 = new Produto(null, "Mesa de escritó rio", 300.00);
        Produto p5 = new Produto(null, "Toalha", 50.00);
        Produto p6 = new Produto(null, "Colcha", 200.00);
        Produto p7 = new Produto(null, "TV true color", 1200.00);
        Produto p8 = new Produto(null, "Roçadeira", 800.00);
        Produto p9 = new Produto(null, "abajour", 100.00);
        Produto p10 = new Produto(null, "Pendente", 180.00);
        Produto p11 = new Produto(null, "Shampoo", 90.00);

        cat1.setProdutos(Arrays.asList(p1, p2, p3));
        cat2.setProdutos(Arrays.asList(p2, p4));
        cat3.setProdutos(Arrays.asList(p5, p6));
        cat4.setProdutos(Arrays.asList(p1, p2, p3));
        cat5.setProdutos(Arrays.asList(p8));
        cat6.setProdutos(Arrays.asList(p9, p10));
        cat7.setProdutos(Arrays.asList(p11));

        p1.setCategorias(Arrays.asList(cat1, cat4));
        p2.setCategorias(Arrays.asList(cat1, cat2, cat4));
        p3.setCategorias(Arrays.asList(cat1));
        p4.setCategorias(Arrays.asList(cat2));
        p5.setCategorias(Arrays.asList(cat3));
        p6.setCategorias(Arrays.asList(cat3));
        p7.setCategorias(Arrays.asList(cat4));
        p8.setCategorias(Arrays.asList(cat5));
        p9.setCategorias(Arrays.asList(cat6));
        p10.setCategorias(Arrays.asList(cat6));
        p11.setCategorias(Arrays.asList(cat7));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        Cliente cli1 = new Cliente(null, "Rafael Neves Gomila", "rafael.gomila@hotmail.com", "36378912377",  TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("123"));
        cli1.setTelefones(Arrays.asList("27363323", "93838393"));

        Cliente cli2 = new Cliente(null, "Luci ", "luciene.rosa111@gmail.com", "3453423445353534534", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("1234"));
        cli2.addPerfil(Perfil.ADMIN);

        Endereco e1 = new Endereco(null, "rua Flores", "300", "apto 203", "jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro", "38777012", cli1, c2);

        cli1.setEnderecos(Arrays.asList(e1, e2));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, simpleDateFormat.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, simpleDateFormat.parse("10/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6L);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, simpleDateFormat.parse("20/10/2017 00:00"), new Date());
        ped2.setPagamento(pagto2);

        cli1.setPedidos(Arrays.asList(ped1, ped2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1L, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2L, 80.0);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1L, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
        clienteRepository.saveAll(Arrays.asList(cli1, cli2));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));
        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}