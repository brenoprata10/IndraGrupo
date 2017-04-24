package com.indra.grupo.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.indra.grupo.dao.EnderecoDAO;
import com.indra.grupo.modelo.Endereco;

@RequestScoped
public class EnderecoService {

	@Inject
	private EnderecoDAO enderecoDAO;
	
	public void cadastrarEndereco(Endereco endereco){
		
		enderecoDAO.persistir(endereco);
		
	}
	
	public void atualizarEndereco(Endereco endereco){
		
		enderecoDAO.atualizar(endereco);
		
	}
	
	public Endereco consultarEnderecoPorID(Integer id){
		
		return enderecoDAO.buscarPorId(Endereco.class, Long.parseLong(id.toString()));
		
	}
	
}
