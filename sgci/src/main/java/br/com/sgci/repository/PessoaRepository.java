package br.com.sgci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sgci.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
		

}
