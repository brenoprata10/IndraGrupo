package com.indra.grupo.services;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

import javax.inject.Inject;

import com.indra.grupo.dao.SenhaDAO;
import com.indra.grupo.dto.ConsultaDTO;
import com.indra.grupo.enums.SenhaEnum;
import com.indra.grupo.modelo.Pessoa;
import com.indra.grupo.modelo.Senha;

public class SenhaService {

	@Inject
	private SenhaDAO senhaDAO;
	
	public void cadastrarSenha(Senha senha){
		
		senhaDAO.persistir(senha);
		
	}
	
	public void atualizarSenha(Senha senha){
		
		senhaDAO.atualizar(senha);
		
	}
	
	public List<Senha> consultar(ConsultaDTO consultaDTO){
		
		return senhaDAO.consultar(consultaDTO);
		
	}
	
	public void atenderSenha(Integer senha){
		
		Senha registro = senhaDAO.consultarSenhaPorId(senha);
		
		registro.setStatus(SenhaEnum.DESATIVA.getStatusSenha());
		
		senhaDAO.atualizar(registro);
		
	}
	
	public void desativarSenha(Integer senha){
		
		Senha registro = senhaDAO.consultarSenhaPorId(senha);
		
		registro.setStatus(SenhaEnum.DESATIVA.getStatusSenha());
		
		senhaDAO.atualizar(registro);
		
	}
	
	public List<Senha> consultarPessoasDesativadas(){
		
		List<Senha> registrosDesativados = senhaDAO.consultarPessoasDesativadas();
		
		return registrosDesativados;
		
	}

	public void removerSenha(String cpf) {

		senhaDAO.remover(senhaDAO.consultarSenhaPorPessoa(cpf));
		
	}
	
}
