package com.rng.apirng.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rng.apirng.domain.Cidade;
import com.rng.apirng.domain.Cliente;
import com.rng.apirng.domain.Endereco;
import com.rng.apirng.domain.enums.Perfil;
import com.rng.apirng.domain.enums.TipoCliente;
import com.rng.apirng.dto.ClienteDTO;
import com.rng.apirng.dto.ClienteNewDTO;
import com.rng.apirng.repositories.CidadeRepository;
import com.rng.apirng.repositories.ClienteRepository;
import com.rng.apirng.repositories.EnderecoRepository;
import com.rng.apirng.security.UserSS;
import com.rng.apirng.services.exception.AuthorizationException;
import com.rng.apirng.services.exception.DataIntegrityException;
import com.rng.apirng.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;

	public List<ClienteDTO> buscarTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteDTO> clientesDTO = clientes.stream().map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());

		return clientesDTO;
	}

	public Page<Cliente> buscarComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return clienteRepository.findAll(pageRequest);
	}

	public Cliente buscarPorId(Long id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Cliente> cliente = clienteRepository.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não enconstrado! ID: " + id));
	}
	
	public Cliente buscarPorEmail(String email) {
		UserSS userSS = UserService.authenticated();
		
		if(userSS == null || !userSS.hasRole(Perfil.ADMIN) && !email.equals(userSS.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		return clienteRepository.findByEmail(email);
	}

	@Transactional
	public Cliente salvar(Cliente cliente) {
		enderecoRepository.saveAll(cliente.getEnderecos());
		return clienteRepository.save(cliente);
	}

	public Cliente alterar(Long id, Cliente novoCliente) {
		Optional<Cliente> velhoCliente = clienteRepository.findById(id);

		novoCliente.setId(velhoCliente.get().getId());
		clienteRepository.save(novoCliente);
		return novoCliente;
	}

	public void deletar(Long id) {
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é pissível excluir porque há entidades relacionadas");
		}
	}

	public Cliente fromDTO(Long id, ClienteDTO clienteDTO) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(),
				cliente.get().getCpfOuCnpj(), cliente.get().getTipoCliente(), null);
	}

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {

		Optional<Cidade> cidade = cidadeRepository.findById(clienteNewDTO.getCidadeId());

		Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(),
				clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()),
				bCryptPasswordEncoder.encode(clienteNewDTO.getSenha()));
		Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente,
				cidade.get());

		cliente.setEnderecos(Arrays.asList(endereco));

		cliente.setTelefones(Arrays.asList(clienteNewDTO.getTelefone1(), clienteNewDTO.getTelefone2(),
				clienteNewDTO.getTelefone3()));

		return cliente;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {

		UserSS userSS = (UserSS) UserService.authenticated();

		if (userSS == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + userSS.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
