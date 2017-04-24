package com.indra.grupo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.indra.grupo.enums.SenhaEnum;
import com.indra.grupo.modelo.Pessoa;

public class PessoaDAO extends BaseDAO<Pessoa>{

	public Pessoa consultarPorCPF(String cpf){

		Map<String, Object> params = new HashMap<String, Object>();

		StringBuilder hql = new StringBuilder("SELECT pessoa FROM Pessoa pessoa ");

		hql.append(" WHERE 0 = 0 ");

		if(cpf != null && !cpf.isEmpty()){

			hql.append(" AND pessoa.cpf = :cpf ");

			params.put("cpf", cpf);

		}

		Query query = getEntityManager().createQuery(hql.toString());

		setParametrosQuery(query, params);

		try{
			
			return (Pessoa) query.getSingleResult();
			
		}catch (NoResultException e) {

			return null;
			
		}

	}

}
