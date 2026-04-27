package br.com.sgci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgci.controller.schema.PessoaFilter;
import br.com.sgci.controller.schema.PessoaReq;
import br.com.sgci.controller.schema.PessoaResponse;
import br.com.sgci.controller.schema.PessoaUpd;
import br.com.sgci.controller.schema.ResponsePagedCommom;
import br.com.sgci.manager.PessoaManager;
import br.com.sgci.model.Pessoa;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Pessoa::Pessoa")
@RestController
@RequestMapping("pessoas")


public class PessoaController {
	
	
	@Autowired
	private PessoaManager pessoaManager;
	
	
	@PostMapping
	public ResponseEntity<Long> createPessoa(@Valid @RequestBody PessoaReq req) {
		Pessoa pessoa = pessoaManager.createPessoa(req);
		return ResponseEntity.ok(pessoa.getId());
		
		}
	
	
	@GetMapping(path = { "{idPessoa}" })
	public ResponseEntity<PessoaResponse> findById(@PathVariable(required=true) Long idPessoa) {
		return ResponseEntity.ok(pessoaManager.findById(idPessoa));
		}
	
	@GetMapping
	public ResponseEntity<ResponsePagedCommom<PessoaResponse>> findAll(@Valid PessoaFilter filtros)	{
		return ResponseEntity.ok(pessoaManager.findAll(filtros));
		}
		
		
		
	@PutMapping(path = { "{idPessoa}" })
	public ResponseEntity<Long> updatePessoa(@Valid 
			@PathVariable(required=true) Long idPessoa, 
			@RequestBody PessoaUpd upd) {
		Pessoa pessoa = pessoaManager.updatePessoa(idPessoa, upd);
		return ResponseEntity.ok(pessoa.getId());
	}
	
	
	@DeleteMapping(path = { "{idPessoa}" })
	public ResponseEntity<Long> deletePessoa(@PathVariable Long idPessoa) {
		pessoaManager.deletePessoa(idPessoa);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/exportar-csv")
	 public ResponseEntity<String> exportarCSV() {
	     return ResponseEntity.ok(pessoaManager.exportarParaBase64());
	 }
		
	

}
