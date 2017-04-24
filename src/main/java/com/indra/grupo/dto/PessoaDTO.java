package com.indra.grupo.dto;

import com.indra.grupo.modelo.Endereco;
import com.indra.grupo.modelo.Estado;
import com.indra.grupo.modelo.Pessoa;
import com.indra.grupo.modelo.Rg;
import com.indra.grupo.modelo.Senha;

public class PessoaDTO {

	private String pessoaNome;
	
	private String pessoaCPF;
	
	private String bairro;
	
	private String cidade;
	
	private String logradouro;
	
	private String CEP;
	
	private String estadoNome;
	
	private String estadoUF;
	
	private String rgNumero;
	
	private String rgOrgaoEmissor;
	
	private Integer senhaId;
	
	private Integer senhaStatus;
	
	private Integer enderecoId;
	
	private Integer rgId;

	public String getPessoaNome() {
		return pessoaNome;
	}

	public void setPessoaNome(String pessoaNome) {
		this.pessoaNome = pessoaNome;
	}

	public String getPessoaCPF() {
		return pessoaCPF;
	}

	public void setPessoaCPF(String pessoaCPF) {
		this.pessoaCPF = pessoaCPF;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getEstadoNome() {
		return estadoNome;
	}

	public void setEstadoNome(String estadoNome) {
		this.estadoNome = estadoNome;
	}

	public String getEstadoUF() {
		return estadoUF;
	}

	public void setEstadoUF(String estadoUF) {
		this.estadoUF = estadoUF;
	}

	public String getRgNumero() {
		return rgNumero;
	}

	public void setRgNumero(String rgNumero) {
		this.rgNumero = rgNumero;
	}

	public String getRgOrgaoEmissor() {
		return rgOrgaoEmissor;
	}

	public void setRgOrgaoEmissor(String rgOrgaoEmissor) {
		this.rgOrgaoEmissor = rgOrgaoEmissor;
	}

	public Integer getSenhaId() {
		return senhaId;
	}

	public void setSenhaId(Integer senhaId) {
		this.senhaId = senhaId;
	}

	public Integer getSenhaStatus() {
		return senhaStatus;
	}

	public void setSenhaStatus(Integer senhaStatus) {
		this.senhaStatus = senhaStatus;
	}

	public Integer getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Integer enderecoId) {
		this.enderecoId = enderecoId;
	}

	public Integer getRgId() {
		return rgId;
	}

	public void setRgId(Integer rgId) {
		this.rgId = rgId;
	}
	
}
