package com.indra.grupo.views.cadastrapessoa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Locale.FilteringMode;

import javax.inject.Inject;

import com.indra.grupo.dto.PessoaDTO;
import com.indra.grupo.enums.EstadoEnum;
import com.indra.grupo.exception.FilaException;
import com.indra.grupo.formulario.CadastrarFormulario;
import com.indra.grupo.modelo.Estado;
import com.indra.grupo.rotas.Rotas;
import com.indra.grupo.services.PessoaService;
import com.indra.grupo.template.VaadinTemplate;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComboBox.CaptionFilter;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@CDIView(value = Rotas.Endereco.CADASTRAR_PESSOA)
@Theme("mytheme")
public class CadastrarPessoaView extends CustomComponent implements View{

	@Inject
	private PessoaService cadastroPessoaService;

	private CadastrarFormulario cadastrarFormulario = new CadastrarFormulario(this);

	private VerticalLayout verticalLayout = new VerticalLayout();
	
	private boolean renderizarReativarSenha = true;

	public void cadastrarPessoaView(){

		Button reativarSenha = new Button("Reativar Registro", FontAwesome.RECYCLE);

		reativarSenha.addStyleName("botaoReativarSenha");

		reativarSenha.addStyleName("alinharDireita");

		CssLayout cssLayout = new CssLayout(reativarSenha);

		cssLayout.addStyleName("alinharDireita");

		Label titulo = new Label("<h2><strong>Cadastrar Pessoa:</strong></h2>", ContentMode.HTML);

		Panel panelCadastro = new Panel("Formulário Cadastro");

		carregarTela();

		panelCadastro.setContent(cadastrarFormulario);

		panelCadastro.addStyleName("hoverable");

		VerticalLayout conteudo = new VerticalLayout(titulo,cssLayout,panelCadastro);

		reativarSenha.addClickListener(e ->{
			
			if(renderizarReativarSenha){

				conteudo.removeStyleName("contentViewTemplate");
				
				verticalLayout.addStyleName("contentViewTemplate2");
				
				verticalLayout.removeStyleName("contentCadastroView");
				
				conteudo.addComponent(montarPanelConsulta(),2);
				
				renderizarReativarSenha = false;
			
			}
			
		});
		
		conteudo.addStyleName("contentViewTemplate");

		conteudo.addStyleName("contentTemplate");

		verticalLayout.addComponents(conteudo);

	}

	private void carregarTela() {

		limitarTamanhoCampos();

		cadastrarFormulario.orgaoEmissor.addValueChangeListener(e -> {

			cadastrarFormulario.orgaoEmissor.setValue(cadastrarFormulario.orgaoEmissor.getValue().toUpperCase());

		});

		List<Estado> estados = EstadoEnum.getListaEstados();

		estados.sort((e1, e2) -> e1.getEstado().compareTo(e2.getEstado()));

		Collection<String> estadosString = new ArrayList<String>();

		Collection<String> ufsString = new ArrayList<String>();

		for (Estado estado : estados) {

			estadosString.add(estado.getEstado());

			ufsString.add(estado.getUf());

		}

		cadastrarFormulario.estado.setItems(estadosString);

		cadastrarFormulario.uf.setItems(ufsString);

		cadastrarFormulario.salvar.addClickListener(e -> {

			realizarCadastroPessoa();

		});

		cadastrarFormulario.cancelar.addClickListener(e -> {

			verificarCancelamento();

		});

		cadastrarFormulario.orgaoEmissor.addValueChangeListener(e -> {

			cadastrarFormulario.orgaoEmissor.setValue(cadastrarFormulario.orgaoEmissor.getValue().toUpperCase());

			if(cadastrarFormulario.orgaoEmissor.getValue().length() == 5){

				if(ufsString.contains(cadastrarFormulario.orgaoEmissor.getValue().substring(3,5))){

					cadastrarFormulario.uf.setSelectedItem(cadastrarFormulario.orgaoEmissor.getValue().substring(3, 5));

				}else{

					cadastrarFormulario.orgaoEmissor.setValue(cadastrarFormulario.orgaoEmissor.getValue().substring(0,3) + cadastrarFormulario.uf.getValue());

				}

			}

		});

		cadastrarFormulario.uf.addValueChangeListener(e -> {

			cadastrarFormulario.orgaoEmissor.setValue(cadastrarFormulario.orgaoEmissor.getValue().substring(0,3) + cadastrarFormulario.uf.getValue());

		});

	}

	private void limitarTamanhoCampos() {

		cadastrarFormulario.cep.setMaxLength(8);
		cadastrarFormulario.bairro.setMaxLength(45);
		cadastrarFormulario.logradouro.setMaxLength(80);
		cadastrarFormulario.cidade.setMaxLength(45);
		cadastrarFormulario.cpf.setMaxLength(11);
		cadastrarFormulario.rg.setMaxLength(7);
		cadastrarFormulario.orgaoEmissor.setMaxLength(5);

	}

	private void verificarCancelamento() {

		PopupView popUpCancelamento = criarPopUpCancelamento("Cancelar Registro");

		popUpCancelamento.setPopupVisible(true);

		if(renderizarReativarSenha){
		
			verticalLayout.addStyleName("contentCadastroView");
		
		}

		verticalLayout.addComponent(popUpCancelamento);

	}

	public PopupView criarPopUpCancelamento(String titulo){

		Label msExclusao = new Label("<h4><strong>Deseja mesmo cancelar o cadastro? <br/><br/> Todas as as informações serão perdidas.</strong></h4>", ContentMode.HTML);

		Button sim = new Button("Sim", FontAwesome.CHECK);

		sim.setDescription("Salve todos os dados importantes.");

		Button nao = new Button("Não", FontAwesome.CLOSE);

		nao.addStyleName("botaoAzul");

		sim.addStyleName("botaoVermelho");

		sim.addStyleName("alinharDireita");

		HorizontalLayout botaoLayout = new HorizontalLayout(sim, nao);

		VerticalLayout layout = new VerticalLayout(msExclusao, botaoLayout);

		PopupView popupView = new PopupView(titulo,layout);

		botaoLayout.setSizeFull();

		popupView.setHideOnMouseOut(false);

		sim.addClickListener(e -> {

			popupView.setPopupVisible(false);

			getUI().getNavigator().navigateTo(Rotas.Endereco.CONSULTAR_PESSOA);

		});

		nao.addClickListener(e -> {

			popupView.setPopupVisible(false);

		});

		return popupView;

	}

	private Component montarPanelConsulta() {

		Panel panel = new Panel("Pessoas com Registro Desativado");

		Grid<PessoaDTO> pessoasDesativadas = new Grid<>(PessoaDTO.class);
		
		pessoasDesativadas.addStyleName("hoverable");

		pessoasDesativadas.setItems(cadastroPessoaService.consultarPessoasDesativadas());

		pessoasDesativadas.setColumns("pessoaNome","pessoaCPF");

		pessoasDesativadas.setCaption("Tabela de Consulta");

		pessoasDesativadas.setResponsive(true);
		
		pessoasDesativadas.setSizeFull();
		
		pessoasDesativadas.addColumn(Pessoa -> "Detalhar",new ButtonRenderer<>( e ->{
			
			getUI().getNavigator().navigateTo(Rotas.Endereco.DETALHAR_PESSOA + "/" + ((PessoaDTO) e.getItem()).getPessoaCPF());
			
		})).setWidth(150).setCaption("Detalhar");
		
		pessoasDesativadas.addColumn(Pessoa -> "Reativar",new ButtonRenderer<>( e ->{
			
			cadastroPessoaService.reativarPessoaNaFila(e.getItem());
			
			mostrarNotificacao("Registro Reativado!", "A Pessoa foi inserida novamente na fila.", Type.HUMANIZED_MESSAGE, 4200, Position.TOP_RIGHT, FontAwesome.CHECK);
			
			pessoasDesativadas.setItems(cadastroPessoaService.consultarPessoasDesativadas());
			
		})).setWidth(136).setCaption("Reativar");

		panel.setContent(pessoasDesativadas);

		return panel;
	}

	private void realizarCadastroPessoa() {

		PessoaDTO cadastroDTO = montarCadastroDTO();

		try {

			removerPinturaCampos(cadastrarFormulario.bairro, cadastrarFormulario.cep,cadastrarFormulario.cidade, cadastrarFormulario.cpf,
					cadastrarFormulario.logradouro,cadastrarFormulario.logradouro, cadastrarFormulario.nome,cadastrarFormulario.orgaoEmissor, cadastrarFormulario.rg,
					cadastrarFormulario.estado, cadastrarFormulario.uf);

			cadastroPessoaService.cadastrarPessoa(cadastroDTO);

			mostrarNotificacao("Cadastro Realizado com Sucesso!", "", Notification.TYPE_HUMANIZED_MESSAGE, 5000, Position.TOP_RIGHT, FontAwesome.SMILE_O);

			getUI().getNavigator().navigateTo(Rotas.Endereco.CONSULTAR_PESSOA);

		} catch (FilaException exception) {

			mostrarNotificacao("Erro no Cadastro",exception.getMessage(), Notification.TYPE_ERROR_MESSAGE,5000,Position.TOP_RIGHT,FontAwesome.FROWN_O);

			pintarCamposVaziosTextField(cadastrarFormulario.bairro, cadastrarFormulario.cep,cadastrarFormulario.cidade, cadastrarFormulario.cpf,
					cadastrarFormulario.logradouro,cadastrarFormulario.logradouro, cadastrarFormulario.nome,cadastrarFormulario.orgaoEmissor, cadastrarFormulario.rg);

			pintarCamposVaziosCombobox(cadastrarFormulario.estado, cadastrarFormulario.uf);

		}

	}



	private void removerPinturaCampos(Object... objects) {

		for (Object object : objects) {

			((Component)object).removeStyleName("bordaVermelha");

			((Component)object).removeStyleName("bordaVermelhaCombo");

		}

	}

	private void pintarCamposVaziosTextField(Object... objects ){

		for (Object object : objects) {

			TextField componente = ((TextField) object);

			if(componente.getValue() == null || componente.getValue().isEmpty()){

				componente.addStyleName("bordaVermelha");

			}

		}

	}

	private void pintarCamposVaziosCombobox(Object... objects ){

		for (Object object : objects) {

			ComboBox<String> componente = ((ComboBox<String>) object);

			if(componente.getValue() == null || componente.getValue().isEmpty()){

				componente.addStyleName("bordaVermelhaCombo");

			}

		}

	}

	private void mostrarNotificacao(String titulo, String message, Type tipoMensagem, int duracao, Position posicao,
			FontAwesome icone) {

		Notification notification = new Notification(titulo,message,tipoMensagem);

		notification.setDelayMsec(duracao);
		notification.setPosition(posicao);
		notification.setIcon(icone);

		notification.show(Page.getCurrent());

	}

	public PessoaDTO montarCadastroDTO() {

		PessoaDTO cadastroDTO = new PessoaDTO();

		cadastroDTO.setEstadoNome(cadastrarFormulario.estado.getValue());
		cadastroDTO.setBairro(cadastrarFormulario.bairro.getValue());
		cadastroDTO.setCEP(cadastrarFormulario.cep.getValue());
		cadastroDTO.setCidade(cadastrarFormulario.cidade.getValue());
		cadastroDTO.setEstadoUF(cadastrarFormulario.uf.getValue());
		cadastroDTO.setLogradouro(cadastrarFormulario.logradouro.getValue());
		cadastroDTO.setPessoaCPF(cadastrarFormulario.cpf.getValue());
		cadastroDTO.setPessoaNome(cadastrarFormulario.nome.getValue());
		cadastroDTO.setRgOrgaoEmissor(cadastrarFormulario.orgaoEmissor.getValue());
		cadastroDTO.setRgNumero(cadastrarFormulario.rg.getValue());

		return cadastroDTO;

	}

	@Override
	public void enter(ViewChangeEvent event) {

		verticalLayout.addComponent(VaadinTemplate.getTemplateMenu(getUI().getNavigator()));

		cadastrarPessoaView();

		setCompositionRoot(verticalLayout);

	}



}
