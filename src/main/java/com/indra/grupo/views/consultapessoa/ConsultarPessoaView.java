package com.indra.grupo.views.consultapessoa;

import java.util.ArrayList;

import java.util.List;
import java.util.Queue;

import javax.inject.Inject;
import javax.inject.Named;

import com.indra.grupo.dao.BaseDAO;
import com.indra.grupo.dto.PessoaDTO;
import com.indra.grupo.dto.ConsultaDTO;
import com.indra.grupo.formulario.ConsultarFormulario;
import com.indra.grupo.formulario.DetalharFormulario;
import com.indra.grupo.modelo.Pessoa;
import com.indra.grupo.modelo.Senha;
import com.indra.grupo.rotas.Rotas;
import com.indra.grupo.services.PessoaService;
import com.indra.grupo.template.VaadinTemplate;
import com.indra.grupo.views.detalharpessoa.DetalharPessoaView;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;

@CDIView(value = Rotas.Endereco.CONSULTAR_PESSOA)
@Theme("mytheme")
public class ConsultarPessoaView extends CustomComponent implements View{
	
	@Inject
	private PessoaService pessoaService;
	
	private ConsultarFormulario consultarFormulario = new ConsultarFormulario(this);
	
	private Grid<PessoaDTO> gridPessoas = new Grid<>(PessoaDTO.class);
	
	private VerticalLayout verticalLayout = new VerticalLayout();
	
	private ConsultaDTO consultaDTO = new ConsultaDTO();
	
	private PessoaDTO cadastroDTO = new PessoaDTO();
	
	private List<PessoaDTO> pessoasConsultadas;

	public void consultarPessoaView() {
		
		//Popular a Grid de Pessoas ========================================================================================================================================

		carregarGridPessoas();

		//Limpar o filtro de pesquisa quando o Botão 'Limpar é pressionado' ================================================================================================

		limparFiltro();

		//Consultar Pessoa =================================================================================================================================================

		consultarPessoas();

		//Criação dos Panels ================================================================================================================================================

		Panel panelFiltro = new Panel("Filtro de Pesquisa");
		
		panelFiltro.setResponsive(true);

		Panel panelTabelaResultado = new Panel("Resultado da Pesquisa");

		definirPanel(panelFiltro, panelTabelaResultado);

		//Título da Página =================================================================================================================================================

		Label titulo = new Label("<h2><strong>Consulta de Pessoas na Fila:</strong></h2>", ContentMode.HTML);
		
		Button atenderPessoaButton = atenderPrimeiraPessoa();
		
		VerticalLayout conteudo = new VerticalLayout(titulo,panelFiltro, atenderPessoaButton, panelTabelaResultado);
		
		conteudo.addStyleName("contentViewTemplate");
		
		conteudo.addStyleName("contentTemplate");
		
		
		verticalLayout.addComponents(conteudo);

		//Configuração Layout ==============================================================================================================================================

		configuracaoLayout(verticalLayout,panelFiltro);
		
	}

	private void carregarGridPessoas() {

		pessoasConsultadas = pessoaService.consultarPessoasNaFila(consultaDTO);
		
		gridPessoas.setItems(pessoasConsultadas);
		
		gridPessoas.setColumns("senhaId","pessoaNome","pessoaCPF");
		
		gridPessoas.addColumn(Pessoa -> "Detalhar",new ButtonRenderer<>( e ->{
			
			getUI().getNavigator().navigateTo(Rotas.Endereco.DETALHAR_PESSOA + "/" + ((PessoaDTO) e.getItem()).getPessoaCPF());
			
		})).setWidth(136).setCaption("Detalhar");
		
		gridPessoas.addColumn(Pessoa -> "Alterar",new ButtonRenderer<>( e ->{
			
			getUI().getNavigator().navigateTo(Rotas.Endereco.ALTERAR_PESSOA + "/" + ((PessoaDTO) e.getItem()).getPessoaCPF());

		})).setWidth(120).setCaption("Alterar");

		gridPessoas.addColumn(Pessoa -> "Excluir",new ButtonRenderer<>( e ->{

			excluirRegistro(e);
			
		})).setWidth(120).setCaption("Excluir");

		gridPessoas.setCaption("Tabela de Consulta");
		
		gridPessoas.setResponsive(true);
		
	}
	
	private void excluirRegistro(RendererClickEvent<PessoaDTO> e) {
		
		PopupView popupView = criarPopupExclusao(null, e.getItem());
		
		popupView.setPopupVisible(true);
		
		verticalLayout.addComponent(popupView);
	}

	public void consultarPessoas(){

		consultarFormulario.consultar.addClickListener(e -> {

			consultaDTO.setNome(consultarFormulario.nomeFiltro.getValue());
			
			consultaDTO.setSenha(consultarFormulario.senhaFiltro.getValue());
			
			pessoasConsultadas = pessoaService.consultarPessoasNaFila(consultaDTO);
			
			gridPessoas.setItems(pessoasConsultadas);
			
			if(pessoasConsultadas == null || pessoasConsultadas.isEmpty()){
			
				mostrarNotificacao("Nenhum Registro Encontrado!", "Verifique os parâmetros da consulta.", Type.WARNING_MESSAGE, 4000, Position.TOP_RIGHT, FontAwesome.INFO_CIRCLE);
				
			}
			
			
		});

	}
	
	private ConsultaDTO montarConsultaDTO() {

		ConsultaDTO consultaDTO = new ConsultaDTO();
		
		consultaDTO.setNome(consultarFormulario.nomeFiltro.getValue());
		
		consultaDTO.setSenha(consultarFormulario.senhaFiltro.getValue());
		
		return consultaDTO;
	}

	public Button atenderPrimeiraPessoa(){
		
		Button atenderPessoaBotao = null;
		
		atenderPessoaBotao = new Button("Atender Primeiro da Fila", FontAwesome.USERS);
		
		atenderPessoaBotao.addStyleName("alinharDireita");
		
		atenderPessoaBotao.addClickListener(e -> {
			
			pessoasConsultadas = pessoaService.consultarPessoasNaFila(new ConsultaDTO());
			
			if(pessoasConsultadas != null && !pessoasConsultadas.isEmpty()){
				
				pessoaService.atenderFila(pessoasConsultadas);
				
				mostrarNotificacao("Senha '" + pessoasConsultadas.get(0).getSenhaId() + "' Atendida com Sucesso!", "", Type.HUMANIZED_MESSAGE, 4000, Position.TOP_RIGHT, FontAwesome.CHECK);
				
				pessoasConsultadas = pessoaService.consultarPessoasNaFila(consultaDTO);
				
				gridPessoas.setItems(pessoasConsultadas);
				
			}else{
				
				mostrarNotificacao("Nenhuma Senha Disponível.", "", Type.WARNING_MESSAGE, 4000, Position.TOP_RIGHT, FontAwesome.WARNING);
				
			}
			
		});
		
		atenderPessoaBotao.addStyleName("botaoAtender");
		
		return atenderPessoaBotao;
		
	}
	
	private void mostrarNotificacao(String titulo, String message, Type tipoMensagem, int duracao, Position posicao,
			FontAwesome icone) {

		Notification notification = new Notification(titulo,message,tipoMensagem);

		notification.setDelayMsec(duracao);
		notification.setPosition(posicao);
		notification.setIcon(icone);

		notification.show(Page.getCurrent());

	}

	public void configuracaoLayout(VerticalLayout verticalLayout, Panel panelFiltro){

		panelFiltro.setSizeFull();

		gridPessoas.setSizeFull();

		verticalLayout.setSizeFull();

		verticalLayout.setResponsive(true);

	}

	/**
	 * Método que configura os paineis
	 * @param panelTabelaResultado 
	 * @param panelFiltro 
	 * 
	 */
	public void definirPanel(Panel panelFiltro, Panel panelTabelaResultado){

		panelFiltro.setContent(consultarFormulario);
		
		panelFiltro.addStyleName("hoverable");

		panelTabelaResultado.setContent(gridPessoas);
		
		panelTabelaResultado.addStyleName("hoverable");

	}

	public void limparFiltro(){

		consultarFormulario.limpar.addClickListener( e -> {

			consultarFormulario.nomeFiltro.clear();

			consultarFormulario.senhaFiltro.clear();

		});
	}

	public PopupView criarPopupExclusao(String titulo, PessoaDTO pessoaDTO){
		
		Label msExclusao = new Label("<h4><strong>Deseja mesmo excluir o registro? </strong></h4>", ContentMode.HTML);
		
		Label nome = new Label("<h4><strong>Nome: </strong></h4>" + pessoaDTO.getPessoaNome(), ContentMode.HTML);
		
		Label cpf = new Label("<h4><strong>CPF: </strong></h4> " + pessoaDTO.getPessoaCPF(), ContentMode.HTML);
		
		nome.addStyleName("templateMarginPopup");
		
		cpf.addStyleName("templateMarginPopup");
		
		Button excluir = new Button("Excluir", FontAwesome.ERASER);
		
		excluir.addClickListener(e -> {
			
			pessoaService.desativarSenha(pessoaDTO);
			
			mostrarNotificacao("Registro Desativado com Sucesso!", "Pode ser reativado em Cadastrar Pessoa - Reativar Registro", Type.HUMANIZED_MESSAGE, 8000, Position.TOP_RIGHT, FontAwesome.TRASH);
			
			pessoasConsultadas = pessoaService.consultarPessoasNaFila(new ConsultaDTO());
			
			gridPessoas.setItems(pessoasConsultadas);
			
		});
		
		Button cancelar = new Button("Cancelar", FontAwesome.CLOSE);
		
		cancelar.addStyleName("botaoAzul");
		
		excluir.addStyleName("botaoVermelho");
		
		HorizontalLayout camposLayout = new HorizontalLayout(nome,cpf);
		
		HorizontalLayout botaoLayout = new HorizontalLayout(excluir, cancelar);
		
		VerticalLayout layout = new VerticalLayout(msExclusao,camposLayout, botaoLayout);
		
		PopupView popupView = new PopupView(titulo,layout);
		
		camposLayout.setSizeFull();
		
		botaoLayout.setSizeFull();
		
		popupView.setHideOnMouseOut(false);
		
		excluir.addClickListener(e -> {
			
			popupView.setPopupVisible(false);
			
		});
		
		cancelar.addClickListener(e -> {
			
			popupView.setPopupVisible(false);
			
		});
		
		return popupView;
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		verticalLayout.addComponent(VaadinTemplate.getTemplateMenu(getUI().getNavigator()));
		
		consultarPessoaView();
		
		setCompositionRoot(verticalLayout);
		
	}

}


