package br.com.sgci.manager;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.sgci.controller.schema.EnderecoMapper;
import br.com.sgci.controller.schema.EnderecoResponse;
import br.com.sgci.controller.schema.PessoaMapper;
import br.com.sgci.controller.schema.PessoaReq;
import br.com.sgci.controller.schema.PessoaResponse;
import br.com.sgci.controller.schema.PessoaUpd;
import br.com.sgci.model.Endereco;
import br.com.sgci.model.Pessoa;
import br.com.sgci.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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
	
	
	public List<PessoaResponse> findAll() {
		
		List<PessoaResponse> listResponse = new ArrayList<PessoaResponse>();
		
		List<Pessoa> listPessoaBd = pessoaRepository.findAll();
		listPessoaBd.forEach(item -> {
			EnderecoResponse enderecoResponse = EnderecoMapper.INSTANCE.toEnderecoResponse(item.getEndereco());
			PessoaResponse pessoaResponse = PessoaMapper.INSTANCE.toPessoaResponse(item, enderecoResponse);
			listResponse.add(pessoaResponse);
		});
		
		return listResponse;
	}
	
	
	@Transactional
	public Pessoa updatePessoa(@Valid Long idPessoa, PessoaUpd upd) {
		
		Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow();
		
		// Atualização dos dados básicos.
		pessoa.setDocumento(upd.documento());
		pessoa.setEstadoCivil(upd.estadoCivil());
		pessoa.setNome(upd.nome());
		pessoa.setProfissao(upd.profissao());
		pessoa.setTipo(upd.tipo());
		
		//Atualização do endereço.
		pessoa.getEndereco().setCep(upd.endereco().cep());
		pessoa.getEndereco().setEstado(upd.endereco().estado());
		pessoa.getEndereco().setCidade(upd.endereco().cidade());
		pessoa.getEndereco().setRua(upd.endereco().rua());
		pessoa.getEndereco().setBairro(upd.endereco().bairro());
		pessoa.getEndereco().setNumero(upd.endereco().numero());
		
		pessoaRepository.save(pessoa);

		return pessoa;
	}

	public PessoaResponse findById(Long idPessoa) {
		Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow();
		EnderecoResponse enderecoResponse = EnderecoMapper.INSTANCE.toEnderecoResponse(pessoa.getEndereco());
		return PessoaMapper.INSTANCE.toPessoaResponse(pessoa, enderecoResponse);
	}

}
