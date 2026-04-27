package br.com.sgci.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.sgci.controller.schema.EnderecoReq;
import br.com.sgci.controller.schema.PessoaReq;
import br.com.sgci.controller.schema.PessoaResponse;
import br.com.sgci.controller.schema.PessoaUpd;
import br.com.sgci.controller.schema.ResponsePagedCommom;
import br.com.sgci.factory.entity.EnderecoFactory;
import br.com.sgci.factory.entity.PessoaFactory;
import br.com.sgci.model.Pessoa;
import br.com.sgci.repository.PessoaRepository;

@ActiveProfiles("test")
@SpringBootTest
class TestPessoaManager {

	@Autowired
	private PessoaManager pessoaManager;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	Pessoa pessoa = null;
	
	@BeforeEach
	void setUp () {
		pessoa = pessoaRepository.save(PessoaFactory.getPessoa(EnderecoFactory.getEndereco()));
	}
	
	@Test
	void Create_Pessoa() {
		
		EnderecoReq enderecoReq = EnderecoFactory.getEnderecoReq();
		PessoaReq req = PessoaFactory.getPessoaReq(enderecoReq, "05137407509");
		
		Pessoa pessoa = pessoaManager.createPessoa(req);
		
		assertTrue(pessoa != null && pessoa.getId() > 0);
		
	}
	
	
	@Test
	void Find_Pessoa_By_Id() {
		PessoaResponse pessoaEncontratada = pessoaManager.findById(pessoa.getId());
		assertTrue(pessoaEncontratada != null && pessoaEncontratada.documento().equals(pessoa.getDocumento()));
	}
	
	
	@Test
	void Find_All_Paged() {
		ResponsePagedCommom<PessoaResponse> retorno = pessoaManager.findAll(PessoaFactory.getPessoaFilter(pessoa.getEndereco().getCep()));
		assertTrue(!retorno.getData().isEmpty() && retorno.getTotalPages() > 0);
	}
	
	
	
	@Test
	void Delete_Pessoa_By_Id() {
		pessoaManager.deletePessoa(pessoa.getId());
		Optional<Pessoa> pessoaOpt = pessoaRepository.findById(pessoa.getId());
		assertTrue(pessoaOpt.isEmpty());
	}

	
	@Test
	void Update_Pessoa_By_Id() {
		PessoaUpd novosDados = PessoaFactory.getPessoaUpd(EnderecoFactory.getEnderecoUpd());
		Pessoa pessoaAtualizada = pessoaManager.updatePessoa(pessoa.getId(), novosDados);
		
		assertTrue(pessoaAtualizada.getNome().equals(novosDados.nome()));
		assertTrue(pessoaAtualizada.getDocumento().equals(novosDados.documento()));
	}
}