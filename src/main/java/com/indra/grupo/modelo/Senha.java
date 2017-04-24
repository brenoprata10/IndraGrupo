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
import javax.persistence.Table;

import com.indra.grupo.interfaces.BaseEntidade;
import com.indra.grupo.tabela.DatabaseTabela;


@Entity
@Table (name = DatabaseTabela.Tabela.SENHA)
public class Senha implements BaseEntidade, Serializable{

	private static final long serialVersionUID = -844066713576473419L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "ID", nullable = false, unique = true, length = 5)
	private Integer id;
	
	@Column (name = "STATUS", nullable = false)
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "PESSOA_CPF", nullable = false)
	private Pessoa pessoa;

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
		Senha other = (Senha) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
