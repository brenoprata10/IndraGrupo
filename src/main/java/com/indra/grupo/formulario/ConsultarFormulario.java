package com.indra.grupo.formulario;

import com.indra.grupo.MyUI;
import com.indra.grupo.consultapessoa.ConsultarFila;
import com.indra.grupo.consultapessoa.ConsultarPessoaView;

public class ConsultarFormulario extends ConsultarFila{

	private static final long serialVersionUID = -1401747158264257366L;
	
	private ConsultarPessoaView myUI;
	
	public ConsultarFormulario(ConsultarPessoaView consultarPessoaView) {
	
		this.myUI = consultarPessoaView;
		
	}

}
