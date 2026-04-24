package br.com.sgci.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.sgci.controller.schema.PessoaReq;
import br.com.sgci.model.Endereco;
import br.com.sgci.model.Pessoa;
import br.com.sgci.repository.PessoaRepository;
import jakarta.transaction.Transactional;

@Service
@Validated
public class PessoaManager {
	
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	
	@Transactional
	public Pessoa createPessoa(PessoaReq req) {
		
		Endereco endereco = new Endereco(
				req.endereco().cep(), 
				req.endereco().estado(), 
				req.endereco().cidade(),
				req.endereco().rua(), 
				req.endereco().bairro(), 
				req.endereco().numero());
		
		
		
		Pessoa pessoa = new Pessoa (
				null, 
				req.nome(), 
				req.tipo(), 
				req.documento(), 
				req.profissao(),
				req.estadoCivil());
		
		 pessoa.setEndereco(endereco);
		
		return pessoaRepository.save(pessoa);

		
		
		
	}

	@Transactional
	public void deletePessoa(Long idPessoa) {
		Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow();
		pessoaRepository.delete(pessoa);
	
		
	}

}
