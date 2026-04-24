package br.com.sgci.controller.schema;

import br.com.sgci.model.EstadoCivilEnum;
import br.com.sgci.model.TipoPessoaEnum;
import jakarta.validation.constraints.NotNull;

public record PessoaUpd(
		
		@NotNull
		String nome,
		
		@NotNull
		EnderecoUpd endereco,
		
		@NotNull
		TipoPessoaEnum tipo,
		
		@NotNull
		String documento,
		
		@NotNull
		String profissao,
		
		@NotNull
		EstadoCivilEnum estadoCivil
	) {
		
	}