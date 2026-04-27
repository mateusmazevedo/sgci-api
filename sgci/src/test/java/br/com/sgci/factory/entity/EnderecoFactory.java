package br.com.sgci.factory.entity;

import br.com.sgci.controller.schema.EnderecoReq;
import br.com.sgci.controller.schema.EnderecoUpd;
import br.com.sgci.model.Endereco;

public class EnderecoFactory {
	
	public static Endereco getEndereco() {
		return new Endereco("79042259", "MS", "Campo Grande", "Av. Afonso Pena", "Centro", 5000);
	}
	
	public static EnderecoReq getEnderecoReq() {
		return new EnderecoReq("79042259", "MS", "Campo Grande", "Av. Afonso Pena", "Centro", 5000);
	}

	public static EnderecoUpd getEnderecoUpd() {
		return new EnderecoUpd("79042886", "SP", "Araçatuba", "Av. 13 de Junho", "Nova Parati", 150);
	}
	
}