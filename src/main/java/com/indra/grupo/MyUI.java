package com.indra.grupo;

import javax.inject.Inject;

import com.indra.grupo.dao.BaseDAO;
import com.indra.grupo.modelo.Pessoa;
import com.indra.grupo.rotas.Rotas;
import com.indra.grupo.template.VaadinTemplate;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.URLMapping;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@CDIUI("")
@Theme("mytheme")
public class MyUI extends UI {
	
	@Inject
	private CDIViewProvider viewProvider;

	private VerticalLayout content = new VerticalLayout();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		
		mostrarNotificacao("Seja Bem Vindo!","", Notification.TYPE_HUMANIZED_MESSAGE, 1900, Position.TOP_CENTER, FontAwesome.HAND_O_RIGHT);
		
		content.setStyleName("contentTemplate");
		
		setStyleName("corFundo");
		
		Navigator navigator = new Navigator(this, this);
		
        navigator.addProvider(viewProvider);
        
        setContent(VaadinTemplate.getTemplateMenu(navigator));
        
        navigator.navigateTo(Rotas.Endereco.TELA_INICIAL);
		
	}
	
	private void mostrarNotificacao(String titulo, String message, Type tipoMensagem, int duracao, Position posicao,
			FontAwesome icone) {

		Notification notification = new Notification(titulo,message,tipoMensagem);
		
		notification.setDelayMsec(duracao);
		notification.setPosition(posicao);
		notification.setIcon(icone);
		
		notification.show(Page.getCurrent());
		
	}

}
