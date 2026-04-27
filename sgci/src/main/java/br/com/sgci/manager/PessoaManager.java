package br.com.sgci.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;

import br.com.sgci.controller.schema.EnderecoMapper;
import br.com.sgci.controller.schema.EnderecoResponse;
import br.com.sgci.controller.schema.PessoaFilter;
import br.com.sgci.controller.schema.PessoaMapper;
import br.com.sgci.controller.schema.PessoaReq;
import br.com.sgci.controller.schema.PessoaResponse;
import br.com.sgci.controller.schema.PessoaUpd;
import br.com.sgci.controller.schema.ResponsePagedCommom;
import br.com.sgci.model.Endereco;
import br.com.sgci.model.Pessoa;
import br.com.sgci.repository.PessoaRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Validated
public class PessoaManager {

	@Autowired
	PessoaRepository pessoaRepository;

	@Transactional
	public Pessoa createPessoa(PessoaReq req) {

		Endereco endereco = new Endereco(req.endereco().cep(), req.endereco().estado(), req.endereco().cidade(),
				req.endereco().rua(), req.endereco().bairro(), req.endereco().numero());

		Pessoa pessoa = new Pessoa(null, req.nome(), req.tipo(), req.documento(), req.profissao(), req.estadoCivil());

		pessoa.setEndereco(endereco);

		return pessoaRepository.save(pessoa);

	}

	@Transactional
	public void deletePessoa(Long idPessoa) {
		Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow();
		pessoaRepository.delete(pessoa);
	}

	public ResponsePagedCommom<PessoaResponse> findAll(@Valid PessoaFilter filtros) {

		Specification<Pessoa> filtrosCustomizados = (root, query, cb) -> {

			List<Predicate> condicoes = new ArrayList<>();

			if (filtros.getId() != null) {
				condicoes.add(cb.equal(root.get("id"), filtros.getId()));
			}

			if (filtros.getNome() != null) {
				condicoes.add(cb.like(root.get("nome"), "%" + filtros.getNome() + "%"));
			}

			if (filtros.getCep() != null) {
				condicoes.add(cb.equal(root.get("endereco").get("cep"), filtros.getCep()));
			}

			if (filtros.getEstado() != null) {
				condicoes.add(cb.equal(root.get("endereco").get("estado"), filtros.getEstado()));
			}

			if (filtros.getCidade() != null) {
				condicoes.add(cb.equal(root.get("endereco").get("cidade"), filtros.getCidade()));
			}

			if (filtros.getTipo() != null) {
				condicoes.add(cb.equal(root.get("tipo"), filtros.getTipo()));
			}

			if (filtros.getDocumento() != null) {
				condicoes.add(cb.equal(root.get("documento"), filtros.getDocumento()));
			}

			if (filtros.getProfissao() != null) {
				condicoes.add(cb.equal(root.get("profissao"), filtros.getProfissao()));
			}

			if (filtros.getEstadoCivil() != null) {
				condicoes.add(cb.equal(root.get("estadoCivil"), filtros.getEstadoCivil()));
			}

			return cb.and(condicoes.toArray(Predicate[]::new));

		};

		Page<Pessoa> listPessoaBd = pessoaRepository.findAll(filtrosCustomizados, PageRequest.of(filtros.getPage(),
				filtros.getSize(), Sort.by(filtros.getDirection(), filtros.getOrdenarPor())));

		List<PessoaResponse> listResponse = new ArrayList<PessoaResponse>();
		listPessoaBd.forEach(item -> {
			EnderecoResponse enderecoResponse = EnderecoMapper.INSTANCE.toEnderecoResponse(item.getEndereco());
			PessoaResponse pessoaResponse = PessoaMapper.INSTANCE.toPessoaResponse(item, enderecoResponse);
			listResponse.add(pessoaResponse);
		});

		return new ResponsePagedCommom<>(
			    listResponse,
			    listPessoaBd.getTotalElements(),
			    listPessoaBd.getTotalPages(),
			    filtros.getSize(),
			    filtros.getPage()
			);
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

		// Atualização do endereço.
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

	public String exportarParaBase64() {

		List<Pessoa> listaPessoasBd = pessoaRepository.findAll();

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				OutputStreamWriter writer = new OutputStreamWriter(outputStream);
				CSVWriter csvWriter = new CSVWriter(writer, ICSVWriter.DEFAULT_SEPARATOR, // Separador de campos no CSV
																							// (por padrão, vírgula)
						ICSVWriter.NO_QUOTE_CHARACTER, // Nenhum caractere de citação será usado
						ICSVWriter.DEFAULT_ESCAPE_CHARACTER, // Caractere de escape padrão
						ICSVWriter.DEFAULT_LINE_END); // Final de linha padrão (por exemplo, "\r\n")
		) {
			String[] header = { "Nome", "Documento", "Profissao", "Estado Civil" };
			csvWriter.writeNext(header);

			listaPessoasBd.forEach(it -> {
				String[] data = { it.getNome(), it.getDocumento(), it.getProfissao(), it.getEstadoCivil().name() };
				csvWriter.writeNext(data);
			});

			writer.flush();

			return Base64.getEncoder().encodeToString(outputStream.toByteArray());

		} catch (IOException e) {
			throw new RuntimeException("Erro ao exportar para Base64", e);
		}
	}
}
