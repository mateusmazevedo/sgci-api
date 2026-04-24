package br.com.sgci.controller.schema;

import br.com.sgci.model.EstadoCivilEnum;
import br.com.sgci.model.TipoPessoaEnum;
import jakarta.validation.constraints.NotNull;

public record PessoaReq (
		
		@NotNull
		String nome,
		
		@NotNull
		EnderecoReq endereco,
		
		@NotNull
		TipoPessoaEnum tipo,
		
		@NotNull
		String documento,
		
		@NotNull
		String profissao,
		EstadoCivilEnum estadoCivil
		
		
		
		
		
		
		) {

}
