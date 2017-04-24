package com.indra.grupo.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.indra.grupo.interfaces.BaseEntidade;
import com.indra.grupo.tabela.DatabaseTabela;

@Entity
@Table (name = DatabaseTabela.Tabela.ENDERECO)
public class Endereco implements BaseEntidade, Serializable{

	private static final long serialVersionUID = -488602698562693380L;

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	@Column (name = "ID", nullable = false, unique = true, length = 5)
	private Integer id;
	
	@Column (name = "LOGRADOURO", nullable = false, length = 45)
	private String logradouro;
	
	@Column (name = "CEP", nullable = true, length = 8)
	private String cep;
	
	@Column(name = "BAIRRO", nullable = false, length = 45)
	private String bairro;
	
	@Column(name = "CIDADE", nullable = false, length = 45)
	private String cidade;
	
	@Column(name = "ESTADO", nullable = false, length = 45)
	private String estado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
