package br.com.sgci.controller.schema;

import br.com.sgci.model.EstadoCivilEnum;
import br.com.sgci.model.TipoPessoaEnum;

public record PessoaResponse(
		
	Long id,
	String nome,
	EnderecoResponse endereco,
	TipoPessoaEnum tipo,
	String documento,
	String profissao,
	EstadoCivilEnum estadoCivil
) {
	
}