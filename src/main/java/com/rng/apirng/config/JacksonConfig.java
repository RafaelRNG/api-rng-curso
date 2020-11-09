package com.rng.apirng.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rng.apirng.domain.PagamentoComBoleto;
import com.rng.apirng.domain.PagamentoComCartao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(){

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder(){

            public void configure(ObjectMapper objectMapper){
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
            }
        };

        return builder;
    }
}
