package com.rng.apirng.services.validation;

import com.rng.apirng.domain.enums.TipoCliente;
import com.rng.apirng.dto.ClienteNewDTO;
import com.rng.apirng.resources.exception.FieldMessage;
import com.rng.apirng.services.validation.utils.BR;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

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

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    e.getMsg())
                    .addPropertyNode(
                            e.getNomeDoCampo())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}