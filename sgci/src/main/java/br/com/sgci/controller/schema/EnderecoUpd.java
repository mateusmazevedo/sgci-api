package br.com.sgci.controller.schema;

import jakarta.validation.constraints.NotNull;

public record EnderecoUpd(
		String cep,
		
		@NotNull
		String estado,
		
		@NotNull
		String cidade,
		
		String rua,
		String bairro,
		Integer numero
	) {

	}