package com.indra.grupo.cadastrapessoa;

import com.indra.grupo.formulario.CadastrarFormulario;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class CadastrarPessoaView implements View{

	private CadastrarFormulario cadastrarFormulario = new CadastrarFormulario(this);
	
	public VerticalLayout cadastrarPessoaView(){
		
		VerticalLayout verticalLayout = new VerticalLayout();
		
		Label titulo = new Label("<h2><strong>Cadastro de Pessoas na Fila:</strong></h2>", ContentMode.HTML);
		
		Panel panelCadastro = new Panel("Formulário Cadastro");
		
		panelCadastro.setContent(cadastrarFormulario);
		
		panelCadastro.addStyleName("hoverable");
		
		verticalLayout.addComponents(titulo,panelCadastro);
		
		verticalLayout.setStyleName("contentViewTemplate");
		
		return verticalLayout;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	
	
}
