package com.indra.grupo.modelo;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.indra.grupo.interfaces.BaseEntidade;
import com.indra.grupo.tabela.DatabaseTabela;
@Entity
@Table(name = DatabaseTabela.Tabela.PESSOA)
public class Pessoa implements BaseEntidade, Serializable {
	
	private static final long serialVersionUID = -4919021511532149280L;
	
	@Id
	@Column (name = "CPF", nullable = false, unique = true, length = 11)
	private String cpf;
	
	@Column(name = "NOME", nullable = false, length = 45)
	private String nome;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENDERECO_ID", nullable = false)
	private Endereco endereco;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RG_RG", nullable = false)
	private Rg rg;

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Rg getRg() {
		return rg;
	}
	public void setRg(Rg rg) {
		this.rg = rg;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
}
