package com.indra.grupo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.indra.grupo.enums.SenhaEnum;
import com.indra.grupo.modelo.Rg;

public class RgDAO extends BaseDAO<Rg>{

	public Rg consultar(String rgNumero, String orgaoEmissor){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder hql = new StringBuilder("SELECT rg FROM Rg rg ");
		
		hql.append(" WHERE 0 = 0 ");
		
		if(rgNumero != null){
			
			hql.append(" AND rg.numeroRg = :rgFiltro ");
			
			params.put("rgFiltro", rgNumero);
			
		}
		
		if(orgaoEmissor != null && !orgaoEmissor.isEmpty()){
			
			hql.append(" AND rg.orgaoEmissor = :orgaoEmissorFiltro ");
			
			params.put("orgaoEmissorFiltro", orgaoEmissor );
			
		}
		
		Query query = getEntityManager().createQuery(hql.toString());
		
		setParametrosQuery(query, params);
		
		try{
			
			return (Rg) query.getSingleResult();
			
		}catch (Exception e) {

			return null;
		
		}
		
	}
	
}
