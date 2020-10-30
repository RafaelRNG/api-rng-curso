package com.rng.apirng.services.validation;

import com.rng.apirng.domain.Cliente;
import com.rng.apirng.domain.enums.TipoCliente;
import com.rng.apirng.dto.ClienteDTO;
import com.rng.apirng.dto.ClienteNewDTO;
import com.rng.apirng.repositories.ClienteRepository;
import com.rng.apirng.resources.exception.FieldMessage;
import com.rng.apirng.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente cliente = clienteRepository.findByEmail(clienteDTO.getEmail());

        if(cliente != null && !cliente.getId().equals(uriId)){
            list.add(new FieldMessage("email", "Email já existente"));
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