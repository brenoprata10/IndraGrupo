package com.indra.grupo.views;

import com.indra.grupo.rotas.Rotas;
import com.indra.grupo.template.VaadinTemplate;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ClassResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@CDIView(value = Rotas.Endereco.TELA_INICIAL)
@Theme("mytheme")
public class TelaInicialView extends CustomComponent implements View{

	private VerticalLayout verticalLayout = new VerticalLayout();
	
	private void telaInicialView() {
		
		Label titulo = new Label("<h2><strong>Tela Inicial:</strong></h2>", ContentMode.HTML);

		Button botaoCadastro = criarButton("Ir para Cadastro", FontAwesome.PLUS);

		Button botaoConsulta = criarButton("Ir para Consulta", FontAwesome.SEARCH);

		Button botaoSair = criarButton("Sair do Sistema", FontAwesome.SIGN_OUT);

		prepararRedirecionamento(botaoCadastro, botaoConsulta, botaoSair);

		Panel panelConsulta = criarPanel("Consultar Pessoas", botaoConsulta,new Image(null, new ThemeResource("img/lupa.png")));
		
		panelConsulta.addStyleName("panelConsulta");
		
		Panel panelCadastro = criarPanel("Cadastrar Pessoas", botaoCadastro,new Image(null, new ThemeResource("img/cadastrar.png")));
		
		panelCadastro.addStyleName("panelCadastrar");

		Panel panelSair = criarPanel("Log Out", botaoSair,new Image(null, new ThemeResource("img/sair.png")));
		
		panelSair.addStyleName("panelSair");

		HorizontalLayout horizontalLayout = new HorizontalLayout(panelConsulta,panelCadastro,panelSair);

		horizontalLayout.setSizeFull();
		
		VerticalLayout conteudo = new VerticalLayout(titulo,horizontalLayout);
		
		conteudo.addStyleName("contentViewTemplate");
		
		conteudo.addStyleName("contentTemplate");
		
		verticalLayout.addComponents(conteudo);

	}
	
	public Button criarButton(String labelButton, Resource icone){

		Button botao = new Button(labelButton, icone);

		botao.setSizeFull();

		return botao;

	}
	
	public void prepararRedirecionamento(Button botaoCadastro, Button botaoConsulta, Button botaoSair) {

		botaoCadastro.addClickListener(e -> {

			getUI().getNavigator().navigateTo(Rotas.Endereco.CADASTRAR_PESSOA);

		});

		botaoConsulta.addClickListener(e -> {

			getUI().getNavigator().navigateTo(Rotas.Endereco.CONSULTAR_PESSOA);

		});

		botaoSair.addClickListener(e -> {
			
			sairSistema();

		});

	}
	
	public Panel criarPanel(String tituloPanel, Button button, Image imagem){

		Panel panelSair = new Panel(tituloPanel);

		panelSair.setContent(new VerticalLayout(imagem, button));

		panelSair.setResponsive(true);

		panelSair.setSizeFull();

		panelSair.addStyleName("hoverable");

		return panelSair;

	}
	
	public void sairSistema(){
		
		Notification.show("Good Bye!! o/");
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		verticalLayout.addComponent(VaadinTemplate.getTemplateMenu(getUI().getNavigator()));
		
		telaInicialView();
		
		setCompositionRoot(verticalLayout);
		
	}
	
}
