package com.indra.grupo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.indra.grupo.dao.EnderecoDAO;
import com.indra.grupo.dao.PessoaDAO;
import com.indra.grupo.dto.PessoaDTO;
import com.indra.grupo.dto.ConsultaDTO;
import com.indra.grupo.enums.SenhaEnum;
import com.indra.grupo.exception.FilaException;
import com.indra.grupo.modelo.Endereco;
import com.indra.grupo.modelo.Pessoa;
import com.indra.grupo.modelo.Rg;
import com.indra.grupo.modelo.Senha;

@RequestScoped
public class PessoaService {

	@Inject
	private PessoaDAO pessoaDAO;
	
	@Inject
	private EnderecoService enderecoService;
	
	@Inject
	private RgService rgService;
	
	@Inject
	private SenhaService senhaService;
	
	public List<PessoaDTO> consultarPessoasNaFila(ConsultaDTO consultaDTO){
		
		List<Senha> registros = senhaService.consultar(consultaDTO);
		
		return montarPessoaDTO(registros); 
		
	}

	private List<PessoaDTO> montarPessoaDTO(List<Senha> registros) {

		List<PessoaDTO> pessoasDTO = new ArrayList<PessoaDTO>();
		
		PessoaDTO pessoaDTO;
		
		for (Senha senha : registros) {
			
			pessoaDTO = new PessoaDTO();
			
			pessoaDTO.setPessoaNome(senha.getPessoa().getNome());
			
			pessoaDTO.setPessoaCPF(senha.getPessoa().getCpf());
			
			pessoaDTO.setSenhaId(senha.getId());
			
			pessoasDTO.add(pessoaDTO);
			
		}
		
		return pessoasDTO;
	}
	
	public List<PessoaDTO> consultarPessoasDesativadas(){
		
		List<Senha> registros = senhaService.consultarPessoasDesativadas();
		
		List<PessoaDTO> pessoas = montarPessoaDTO(registros);
		
		return pessoas;
		
	}
	
	public void reativarPessoaNaFila(PessoaDTO cadastroDTO){
		
		Pessoa pessoa = new Pessoa();

		pessoa.setCpf(cadastroDTO.getPessoaCPF());

		pessoa.setNome(cadastroDTO.getPessoaNome());
		
		pessoa.setEndereco(montarEndereco(cadastroDTO));
		
		pessoa.setRg(montarRG(cadastroDTO));
		
		senhaService.removerSenha(cadastroDTO.getPessoaCPF());
		
		senhaService.cadastrarSenha(montarSenha(pessoa));
		
	}
	
	public void atenderFila(List<PessoaDTO> pessoasConsultadas){
		
		senhaService.atenderSenha(pessoasConsultadas.get(0).getSenhaId());
		
	}
	
	public void desativarSenha(PessoaDTO pessoaDTO){
		
		senhaService.desativarSenha(pessoaDTO.getSenhaId());
		
	}

	public void cadastrarPessoa(PessoaDTO cadastroDTO) throws FilaException{

		validarCamposObrigatorios(cadastroDTO);
		
		validarCamposRepetidos(cadastroDTO);

		Pessoa pessoa = new Pessoa();

		pessoa.setCpf(cadastroDTO.getPessoaCPF());

		pessoa.setNome(cadastroDTO.getPessoaNome());
		
		pessoa.setEndereco(montarEndereco(cadastroDTO));
		
		pessoa.setRg(montarRG(cadastroDTO));
		
		enderecoService.cadastrarEndereco(pessoa.getEndereco());
		
		rgService.cadastrarRg(pessoa.getRg());
		
		pessoaDAO.persistir(pessoa);
		
		senhaService.cadastrarSenha(montarSenha(pessoa));

	}
	
	public void atualizarPessoa(PessoaDTO cadastroDTO) throws FilaException{
		
		validarCamposObrigatorios(cadastroDTO);
		
		Rg registrosRG = rgService.consultarRg(cadastroDTO.getRgNumero(), cadastroDTO.getRgOrgaoEmissor());
		
		if(registrosRG != null && !registrosRG.getId().equals(cadastroDTO.getRgId())){
			
			throw new FilaException("RG já cadastrado.");
			
		}
		
		Pessoa pessoa = new Pessoa();

		pessoa.setCpf(cadastroDTO.getPessoaCPF());

		pessoa.setNome(cadastroDTO.getPessoaNome());
		
		pessoa.setEndereco(montarEndereco(cadastroDTO));
		
		pessoa.setRg(montarRG(cadastroDTO));
		
		enderecoService.atualizarEndereco(pessoa.getEndereco());
		
		rgService.atualizarRg(pessoa.getRg());
		
		pessoaDAO.atualizar(pessoa);
		
	}
	
	public PessoaDTO consultarPorCPF(String cpf){
		
		Pessoa pessoa = pessoaDAO.consultarPorCPF(cpf);
		
		PessoaDTO pessoaDTO = new PessoaDTO();
		
		pessoaDTO = montarPessoaDTO(pessoa);
		
		return pessoaDTO;
		
	}

	private PessoaDTO montarPessoaDTO(Pessoa pessoa) {
		
		PessoaDTO pessoaDTO = new PessoaDTO();
		
		pessoaDTO.setBairro(pessoa.getEndereco().getBairro());
		pessoaDTO.setCEP(pessoa.getEndereco().getCep());
		pessoaDTO.setCidade(pessoa.getEndereco().getCidade());
		pessoaDTO.setEstadoNome(pessoa.getEndereco().getEstado());
		pessoaDTO.setEstadoUF(pessoa.getRg().getUf());
		pessoaDTO.setEnderecoId(pessoa.getEndereco().getId());
		pessoaDTO.setRgId(pessoa.getRg().getId());
		pessoaDTO.setLogradouro(pessoa.getEndereco().getLogradouro());
		pessoaDTO.setPessoaCPF(pessoa.getCpf());
		pessoaDTO.setPessoaNome(pessoa.getNome());
		pessoaDTO.setRgNumero(pessoa.getRg().getNumeroRg());
		pessoaDTO.setRgOrgaoEmissor(pessoa.getRg().getOrgaoEmissor());
		
		return pessoaDTO;
	}

	private void validarCamposRepetidos(PessoaDTO cadastroDTO) throws FilaException {

		Pessoa registroCPF = pessoaDAO.consultarPorCPF(cadastroDTO.getPessoaCPF());
		
		Rg registrosRG = rgService.consultarRg(cadastroDTO.getRgNumero(), cadastroDTO.getRgOrgaoEmissor());
		
		if(registroCPF != null){
			
			throw new FilaException("CPF já cadastrado.");
			
		}
		
		if(registrosRG != null){
			
			throw new FilaException("RG já cadastrado.");
			
		}
		
	}

	private Senha montarSenha(Pessoa pessoa) {

		Senha senha = new Senha();
		
		senha.setPessoa(pessoa);
		
		senha.setStatus(SenhaEnum.ATIVA.getStatusSenha());
		
		return senha;
	}

	private Rg montarRG(PessoaDTO cadastroDTO) {

		Rg rg = new Rg();
		
		rg.setId(cadastroDTO.getRgId());
		
		rg.setOrgaoEmissor(cadastroDTO.getRgOrgaoEmissor());
		
		rg.setNumeroRg(cadastroDTO.getRgNumero());
		
		rg.setUf(cadastroDTO.getEstadoUF());
		
		return rg;
	}

	private Endereco montarEndereco(PessoaDTO cadastroDTO) {

		Endereco endereco = new Endereco();
		
		endereco.setId(cadastroDTO.getEnderecoId());
		
		endereco.setBairro(cadastroDTO.getBairro());
		
		endereco.setCep(cadastroDTO.getCEP());
		
		endereco.setCidade(cadastroDTO.getCidade());
		
		endereco.setEstado(cadastroDTO.getEstadoNome());
		
		endereco.setLogradouro(cadastroDTO.getLogradouro());
		
		return endereco;
	}

	private void validarCamposObrigatorios(PessoaDTO cadastroDTO) throws FilaException{

		validarCamposNulos(cadastroDTO.getBairro(), cadastroDTO.getCEP(), cadastroDTO.getCidade(), cadastroDTO.getEstadoNome(), cadastroDTO.getEstadoUF(),
				cadastroDTO.getLogradouro(), cadastroDTO.getPessoaCPF(), cadastroDTO.getPessoaNome(), cadastroDTO.getRgNumero(), cadastroDTO.getRgOrgaoEmissor());
		
		if(!isCPF(cadastroDTO.getPessoaCPF())){
			throw new FilaException("Por favor verifique o número do CPF.");
		}
		
		if(cadastroDTO.getRgOrgaoEmissor().length() < 5){
			
			throw new FilaException("Orgão Emissor Incorreto.");
			
		}
		
		if(cadastroDTO.getCEP().length() != 8){
			
			throw new FilaException("O CEP deve conter 8 dígitos.");
			
		}
		
		try{
			
			Integer.parseInt(cadastroDTO.getCEP());
			
		}catch(NumberFormatException exception){
			
			throw new FilaException("O CEP deve conter apenas números.");
			
		}
		
	}
	
	private void validarCamposNulos(Object... objects) throws FilaException{
		
		for (Object object : objects) {
			
			if(object == null || ((String) object).isEmpty()){
				
				throw new FilaException("Todos os campos devem ser preenchidos.");
				
			}
			
		}
		
	}

	public boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
				CPF.equals("22222222222") || CPF.equals("33333333333") ||
				CPF.equals("44444444444") || CPF.equals("55555555555") ||
				CPF.equals("66666666666") || CPF.equals("77777777777") ||
				CPF.equals("88888888888") || CPF.equals("99999999999") ||
				(CPF.length() != 11))
			return(false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i=0; i<9; i++) {              
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0         
				// (48 eh a posicao de '0' na tabela ASCII)         
				num = (int)(CPF.charAt(i) - 48); 
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for(i=0; i<10; i++) {
				num = (int)(CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else dig11 = (char)(r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return(true);
			else return(false);
		} catch (InputMismatchException erro) {
			return(false);
		}
	}

	public static String imprimeCPF(String CPF) {
		return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
				CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
	}

}
