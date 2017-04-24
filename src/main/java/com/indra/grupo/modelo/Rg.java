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
@Table (name = DatabaseTabela.Tabela.RG)
public class Rg implements BaseEntidade, Serializable{

	private static final long serialVersionUID = 8234691090673499485L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column (name = "UF", nullable = false, length = 2)
	private String uf;
	
	@Column (name = "ORGAO_EMISSOR", nullable = false, length = 5)
	private String orgaoEmissor;
	
	@Column (name = "NUMERO_RG", nullable = false, length = 7)
	private String numeroRg;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroRg() {
		return numeroRg;
	}

	public void setNumeroRg(String numeroRg) {
		this.numeroRg = numeroRg;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroRg == null) ? 0 : numeroRg.hashCode());
		result = prime * result + ((orgaoEmissor == null) ? 0 : orgaoEmissor.hashCode());
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
		Rg other = (Rg) obj;
		if (numeroRg == null) {
			if (other.numeroRg != null)
				return false;
		} else if (!numeroRg.equals(other.numeroRg))
			return false;
		if (orgaoEmissor == null) {
			if (other.orgaoEmissor != null)
				return false;
		} else if (!orgaoEmissor.equals(other.orgaoEmissor))
			return false;
		return true;
	}

}
