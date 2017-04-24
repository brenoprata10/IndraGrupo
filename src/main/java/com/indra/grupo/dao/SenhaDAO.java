package com.indra.grupo.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.persistence.Query;

import com.indra.grupo.dto.ConsultaDTO;
import com.indra.grupo.enums.SenhaEnum;
import com.indra.grupo.modelo.Pessoa;
import com.indra.grupo.modelo.Senha;

public class SenhaDAO extends BaseDAO<Senha>{

	public List<Senha> consultar(ConsultaDTO consultaDTO){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder hql = new StringBuilder("SELECT senha FROM Senha senha ");
		
		hql.append(" LEFT JOIN FETCH senha.pessoa pessoa ");
		
		hql.append(" WHERE 0 = 0 ");
		
		if(consultaDTO.getNome() != null && !consultaDTO.getNome().isEmpty()){
			
			hql.append(" AND UPPER(pessoa.nome) LIKE UPPER(:nomeFiltro) ");
			
			params.put("nomeFiltro", "%" + consultaDTO.getNome() + "%");
			
		}
		
		if(consultaDTO.getSenha() != null && !consultaDTO.getSenha().isEmpty()){
			
			hql.append(" AND senha.id = :senhaFiltro ");
			
			params.put("senhaFiltro", Integer.parseInt(consultaDTO.getSenha()) );
			
		}
		
		hql.append(" AND senha.status =  ").append(SenhaEnum.ATIVA.getStatusSenha());
		
		hql.append(" ORDER BY senha.id ASC ");
		
		Query query = getEntityManager().createQuery(hql.toString());
		
		setParametrosQuery(query, params);
		
		return query.getResultList();
	}
	
	public Senha consultarSenhaPorId(Integer senhaFiltro){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder hql = new StringBuilder("SELECT senha FROM Senha senha ");
		
		hql.append(" LEFT JOIN FETCH senha.pessoa pessoa ");
		
		hql.append(" WHERE 0 = 0 ");
		
		if(senhaFiltro != null){
			
			hql.append(" AND senha.id = :senhaFiltro ");
			
			params.put("senhaFiltro", senhaFiltro );
			
		}
		
		Query query = getEntityManager().createQuery(hql.toString());
		
		setParametrosQuery(query, params);
		try{
			
			return (Senha) query.getSingleResult();
			
		}catch (Exception e){
			
			return null;
			
		}
		
	}
	
	public List<Senha> consultarPessoasDesativadas(){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder hql = new StringBuilder("SELECT senha FROM Senha senha ");
		
		hql.append(" LEFT JOIN FETCH senha.pessoa pessoa ");
		
		hql.append(" WHERE 0 = 0 ");
		
		hql.append(" AND senha.status =  ").append(SenhaEnum.DESATIVA.getStatusSenha());
		
		hql.append(" ORDER BY pessoa.nome ASC ");
		
		Query query = getEntityManager().createQuery(hql.toString());
		
		setParametrosQuery(query, params);
		
		return query.getResultList();
		
	}
	
	public Senha consultarSenhaPorPessoa(String cpf){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder hql = new StringBuilder("SELECT senha FROM Senha senha ");
		
		hql.append(" LEFT JOIN FETCH senha.pessoa pessoa ");
		
		hql.append(" WHERE 0 = 0 ");

		if(cpf != null && !cpf.isEmpty()){
			
			hql.append(" AND pessoa.cpf = :cpfFiltro ");
			
			params.put("cpfFiltro", cpf );
			
			
		}
		
		Query query = getEntityManager().createQuery(hql.toString());
		
		setParametrosQuery(query, params);
		
		try{
			
			return (Senha) query.getSingleResult();
			
		}catch (Exception e){
			
			return null;
			
		}
		
	}
	
}
