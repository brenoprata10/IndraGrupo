package com.indra.grupo.formulario;

import com.indra.grupo.views.consultapessoa.ConsultarPessoaView;
import com.indra.grupo.views.detalharpessoa.DetalharPessoa;
import com.indra.grupo.views.detalharpessoa.DetalharPessoaView;

public class DetalharFormulario extends DetalharPessoa{

	private static final long serialVersionUID = 8926000598339604666L;
	
	private DetalharPessoaView detalharPessoa;
	
	private ConsultarPessoaView consultarPessoa;
	
	public DetalharFormulario(DetalharPessoaView myUi) {

		this.detalharPessoa = myUi;
	
	}

	public DetalharFormulario(ConsultarPessoaView consultarPessoa) {
		
		this.consultarPessoa = consultarPessoa;
		
	}
}
