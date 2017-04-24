package com.indra.grupo.services;

import java.util.List;

import javax.inject.Inject;

import com.indra.grupo.dao.RgDAO;
import com.indra.grupo.modelo.Rg;

public class RgService {

	@Inject
	private RgDAO rgDAO;
	
	public void cadastrarRg(Rg rg){
		
		rgDAO.persistir(rg);
		
	}
	
	public void atualizarRg(Rg rg){
		
		rgDAO.atualizar(rg);
		
	}
	
	public Rg consultarRg(String rgNumero, String orgaoEmissor){
		
		return rgDAO.consultar(rgNumero, orgaoEmissor);
		
	}
	
}
