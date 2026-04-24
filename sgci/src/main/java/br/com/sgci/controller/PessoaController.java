package br.com.sgci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgci.controller.schema.PessoaReq;
import br.com.sgci.manager.PessoaManager;
import br.com.sgci.model.Pessoa;
import jakarta.validation.Valid;



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
	
	
	//@GetMapping
	//public ResponseEntity<String> findAll(){
		//return ResponseEntity.ok("Olá mundo!");
		
		
		
	//@PutMapping
	
	
	
	@DeleteMapping(path = { "{idPessoa}" })
	public ResponseEntity<Long> deletePessoa(@PathVariable Long idPessoa) {
		pessoaManager.deletePessoa(idPessoa);
		return ResponseEntity.ok().build();
	}
		
	

}
