package br.com.vr.minauth.configuration;

import br.com.vr.minauth.api.request.CardRequest;
import br.com.vr.minauth.api.response.CardResponse;
import br.com.vr.minauth.domain.entity.CardEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CardRequest.class, CardEntity.class)
                .<String>addMapping(src -> src.getNumeroCartao(), (dest, value) -> dest.setNumber(value))
                .<String>addMapping(src -> src.getSenha(), (dest, value) -> dest.setPassword(value));

        modelMapper.createTypeMap(CardEntity.class, CardResponse.class)
                .<String>addMapping(src -> src.getNumber(), (dest, value) -> dest.setNumeroCartao(value))
                .<String>addMapping(src -> src.getPassword(), (dest, value) -> dest.setSenha(value));

        return modelMapper;
    }
}
