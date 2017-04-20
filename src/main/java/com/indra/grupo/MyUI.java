package com.indra.grupo;

import com.indra.grupo.cadastrapessoa.CadastrarPessoa;
import com.indra.grupo.cadastrapessoa.CadastrarPessoaView;
import com.indra.grupo.consultapessoa.ConsultarPessoaView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
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
@Theme("mytheme")
public class MyUI extends UI {

	private ConsultarPessoaView consultarPessoaView = new ConsultarPessoaView();
	
	private CadastrarPessoaView cadastrarPessoa = new CadastrarPessoaView();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	final VerticalLayout layout = new VerticalLayout();
    	
    	VerticalLayout content = new VerticalLayout();
    	
    	content.setStyleName("contentTemplate");
    	
    	setStyleName("corFundo");
    	
    	layout.setStyleName("corFundo");
        
    	MenuBar menuBar = new MenuBar();
    	
    	MenuBar.Command command = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				
				if(selectedItem.getText().equals("Consultar Pessoa")){
					
					content.setSizeFull();
					
					content.removeAllComponents();
		        	
		        	content.addComponent(consultarPessoaView.consultarPessoaView());
					
				}
				
				if(selectedItem.getText().equals("Cadastrar Pessoa")){
					
					content.removeAllComponents();
					
		        	content.addComponents(cadastrarPessoa.cadastrarPessoaView());
					
				}
				
			}
		};
		
		Panel panel = new Panel();
		
		panel.setContent(menuBar);
		
		panel.addStyleName("panelHeader");
		
		menuBar.addItem("Sair",FontAwesome.SIGN_OUT, command);
		
		menuBar.addItem("Cadastrar Pessoa",FontAwesome.PLUS ,command);
		
		menuBar.addItem("Consultar Pessoa",FontAwesome.SEARCH ,command);
		
		menuBar.addItem("Tela Inicial",FontAwesome.HOME,command);
		
        //Definir Template =================================================================================================================================================
        
        layout.addComponents(panel, content);
        
        layout.setResponsive(true);
        
        setContent(layout);
        
    }
    
    
    
   /* @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    	
    	
    }*/
}
