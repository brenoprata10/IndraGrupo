package com.indra.grupo.template;

import static com.indra.grupo.rotas.Rotas.*;

import com.indra.grupo.rotas.Rotas.Endereco;
import com.indra.grupo.rotas.Rotas.Nome;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class VaadinTemplate {
	
	public static VerticalLayout getTemplateMenu(Navigator navigator){
		
		final VerticalLayout layout = new VerticalLayout();

		layout.setStyleName("corFundo");

		MenuBar menuBar = new MenuBar();

		MenuBar.Command command = new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {

				if(selectedItem.getText().equals(Nome.TELA_INICIAL)){
					
					navigator.navigateTo(Endereco.TELA_INICIAL);

				}

				if(selectedItem.getText().equals(Nome.CONSULTAR_PESSOA)){

					navigator.navigateTo(Endereco.CONSULTAR_PESSOA);

				}

				if(selectedItem.getText().equals(Nome.CADASTRAR_PESSOA)){

					navigator.navigateTo(Endereco.CADASTRAR_PESSOA);

				}
				
				if(selectedItem.getText().equals(Nome.SAIR)){

					Notification.show("Saindo...");
					
					navigator.navigateTo(Endereco.TELA_INICIAL);


				}

			}
		};

		Panel panel = new Panel();

		panel.setContent(menuBar);

		panel.addStyleName("panelHeader");

		menuBar.addItem(Nome.SAIR,FontAwesome.SIGN_OUT, command);

		menuBar.addItem(Nome.CADASTRAR_PESSOA,FontAwesome.PLUS ,command);

		menuBar.addItem(Nome.CONSULTAR_PESSOA,FontAwesome.SEARCH ,command);

		menuBar.addItem(Nome.TELA_INICIAL,FontAwesome.HOME,command);

		//Definir Template =================================================================================================================================================

		layout.addComponents(panel);

		layout.setResponsive(true);
		
		return layout;

		
	}
	
}
