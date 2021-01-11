package com.rng.apirng.services.validation;

import com.rng.apirng.domain.Cliente;
import com.rng.apirng.domain.enums.TipoCliente;
import com.rng.apirng.dto.ClienteNewDTO;
import com.rng.apirng.repositories.ClienteRepository;
import com.rng.apirng.resources.exception.FieldMessage;
import com.rng.apirng.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())){

            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())){

            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente cliente = clienteRepository.findByEmail(clienteNewDTO.getEmail());

        if(cliente != null){
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMsg()).addPropertyNode(e.getNomeDoCampo()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}