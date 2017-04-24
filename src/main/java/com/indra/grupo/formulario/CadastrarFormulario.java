package com.indra.grupo.formulario;

import com.indra.grupo.views.cadastrapessoa.CadastrarPessoa;
import com.indra.grupo.views.cadastrapessoa.CadastrarPessoaView;

public class CadastrarFormulario extends CadastrarPessoa{

	private static final long serialVersionUID = -550654922117526762L;
	
	private CadastrarPessoaView myUi;
	
	public CadastrarFormulario(CadastrarPessoaView cadastrarPessoaView) {

		this.myUi = cadastrarPessoaView;
	
	}

}
