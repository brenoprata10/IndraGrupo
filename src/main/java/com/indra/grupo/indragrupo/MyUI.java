package com.indra.grupo.indragrupo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.indra.grupo.indragrupo.entidade.Pessoa;
import com.indra.grupo.indragrupo.formulario.ConsultarFormulario;
import com.indra.grupo.indragrupo.formulario.TabelaPessoasFormulario;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private ConsultarFormulario consultarFormulario = new ConsultarFormulario(this);
	
	private TabelaPessoasFormulario tabelaPessoasFormulario = new TabelaPessoasFormulario(this);
	
	private Grid<Pessoa> gridPessoas = new Grid<>(Pessoa.class);
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	//Popular a Grid de Pessoas ========================================================================================================================================
    	
    	carregarGridPessoas();
    	
        //Limpar o filtro de pesquisa quando o Botão 'Limpar é pressionado' ================================================================================================
        
        limparFiltro();
        
        //Consultar Pessoa =================================================================================================================================================
        
        consultarPessoa();
        
        //Definir Template =================================================================================================================================================
        
        final VerticalLayout layout = new VerticalLayout();
        
        tabelaPessoasFormulario.addComponent(gridPessoas);
        
        //Criação dos Panels ================================================================================================================================================
        
        Panel panelFiltro = new Panel("Filtro de Pesquisa");
        
        Panel panelTabelaResultado = new Panel("Resultado da Pesquisa");
        
        definirPanel(panelFiltro, panelTabelaResultado);

        //Título da Página =================================================================================================================================================
        
        Label titulo = new Label("<h2><strong>Consulta de Pessoas na Fila:</strong></h2>", ContentMode.HTML);
        
        VerticalLayout verticalLayout = new VerticalLayout(panelFiltro, panelTabelaResultado);
        
        //Configuração Layout ==============================================================================================================================================
        
        configuracaoLayout(verticalLayout,panelFiltro, layout);
        
        layout.addComponents(titulo, verticalLayout);
        
        setContent(layout);
        
    }
    
    public void consultarPessoa(){
    	
    	consultarFormulario.consultar.addClickListener(e -> {
    		
    		
    	});
    	
    }
    
    
    public void configuracaoLayout(VerticalLayout verticalLayout, Panel panelFiltro, VerticalLayout layout){
    	
    	panelFiltro.setSizeFull();
        
        gridPessoas.setSizeFull();
        
        verticalLayout.setSizeFull();
        
        verticalLayout.setResponsive(true);
        
        layout.setResponsive(true);
    	
    }
    
    /**
     * Método que configura os paineis
     * @param panelTabelaResultado 
     * @param panelFiltro 
     * 
     */
    public void definirPanel(Panel panelFiltro, Panel panelTabelaResultado){
    	
        panelFiltro.setContent(consultarFormulario);
        
        panelTabelaResultado.setContent(tabelaPessoasFormulario);
    	
    }
    
    public void limparFiltro(){
    	
    	consultarFormulario.limpar.addClickListener( e -> {
    	
	    	consultarFormulario.nomeFiltro.clear();
	    	
	    	consultarFormulario.senhaFiltro.clear();
    	
    	});
    }
    
    public List<Pessoa> carregarLista(){
    	
    	List<Pessoa> pessoas = new ArrayList<Pessoa>();
    	
    	Pessoa pessoa;
    	
    	Integer cont;
    	
    	for(cont = 0; cont <= 20; cont++){
    		
    		pessoa = new Pessoa();
    		
    		pessoa.setNome("Pessoa " + cont);
    		
    		StringBuilder cpf = new StringBuilder(cont.toString() + cont.toString() + cont.toString() + cont.toString() + cont.toString() + cont.toString() +cont.toString() + cont.toString());
    		
    		pessoa.setCpf(cpf.toString());
    		
    		pessoas.add(pessoa);
    		
    	}
    	
    	//pessoas.sort((p1, p2) -> p1.getNome().compareTo(p2.getNome()));
    	
    	return pessoas; 
    	
    }
    
    public void carregarGridPessoas(){
    	
    	gridPessoas.setItems(carregarLista());
    	
    	gridPessoas.setColumns("nome", "cpf");
    	
    	gridPessoas.addColumn(Pessoa -> "Alterar",new ButtonRenderer<>( e ->{
    		
    		consultarFormulario.nomeFiltro.setValue("ALTEREI");
    		
    	}));
    	
    	gridPessoas.addColumn(Pessoa -> "Excluir",new ButtonRenderer<>( e ->{
    		
    		consultarFormulario.nomeFiltro.setValue("DELETEI");
    		
    	}));
    	
    	gridPessoas.setColumnOrder("nome", "cpf");
    	
    	gridPessoas.setCaption("Tabela de Consulta");
    	
    }
    
   /* @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    	
    	
    }*/
}
