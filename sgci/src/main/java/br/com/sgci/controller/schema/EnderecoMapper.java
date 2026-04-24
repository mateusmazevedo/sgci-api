package br.com.sgci.controller.schema;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.sgci.model.Endereco;

@Mapper
public interface EnderecoMapper {
	EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);
	
	EnderecoResponse toEnderecoResponse(Endereco endereco);
}