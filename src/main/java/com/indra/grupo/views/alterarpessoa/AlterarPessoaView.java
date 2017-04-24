package com.indra.grupo.views.alterarpessoa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.indra.grupo.dto.PessoaDTO;
import com.indra.grupo.enums.EstadoEnum;
import com.indra.grupo.exception.FilaException;
import com.indra.grupo.formulario.AlterarPessoaFormulario;
import com.indra.grupo.formulario.DetalharFormulario;
import com.indra.grupo.modelo.Estado;
import com.indra.grupo.rotas.Rotas;
import com.indra.grupo.services.PessoaService;
import com.indra.grupo.template.VaadinTemplate;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

@CDIView(value = Rotas.Endereco.ALTERAR_PESSOA)
public class AlterarPessoaView extends CustomComponent implements View{

	@Inject
	private PessoaService pessoaService;
	
	private AlterarPessoaFormulario alterarPessoaFormulario = new AlterarPessoaFormulario(this);
	
	private VerticalLayout verticalLayout = new VerticalLayout();
	
	private PessoaDTO pessoaDTO;
	
	public void alterarPessoaView(){
		
		carregarTela();
		
		Button retornarConsulta = new Button("Voltar Para Consulta", FontAwesome.ARROW_LEFT);
		
		retornarConsulta.setStyleName("botaoVoltarConsulta");
		
		retornarConsulta.addClickListener(e ->{
			
			verificarCancelamento();
			
		});
		
		CssLayout cssLayout = new CssLayout(retornarConsulta);
		
		Label titulo = new Label("<h2><strong>Alterar Pessoa:</strong></h2>", ContentMode.HTML);
		
		Panel panelCadastro = new Panel("Detalhar Formulário");
		
		carregarCamposPessoa();
		
		panelCadastro.setContent(alterarPessoaFormulario);
		
		panelCadastro.addStyleName("hoverable");
		
		VerticalLayout conteudo = new VerticalLayout(cssLayout,titulo,panelCadastro);
		
		conteudo.addStyleName("contentViewTemplate");
		
		conteudo.addStyleName("contentTemplate");
		
		verticalLayout.addComponents(conteudo);
		
	}
	
	private void carregarTela() {

		List<Estado> estados = EstadoEnum.getListaEstados();

		estados.sort((e1, e2) -> e1.getEstado().compareTo(e2.getEstado()));

		Collection<String> estadosString = new ArrayList<String>();

		Collection<String> ufsString = new ArrayList<String>();

		for (Estado estado : estados) {

			estadosString.add(estado.getEstado());

			ufsString.add(estado.getUf());

		}

		alterarPessoaFormulario.estado.setItems(estadosString);

		alterarPessoaFormulario.uf.setItems(ufsString);
		
		alterarPessoaFormulario.salvar.addClickListener(e -> {

			realizarCadastroPessoa();

		});
		
		alterarPessoaFormulario.orgaoEmissor.addValueChangeListener(e -> {

			alterarPessoaFormulario.orgaoEmissor.setValue(alterarPessoaFormulario.orgaoEmissor.getValue().toUpperCase());
			
			if(alterarPessoaFormulario.orgaoEmissor.getValue().length() == 5){
			
				if(ufsString.contains(alterarPessoaFormulario.orgaoEmissor.getValue().substring(3,5))){

					alterarPessoaFormulario.uf.setSelectedItem(alterarPessoaFormulario.orgaoEmissor.getValue().substring(3, 5));
					
				}else{
					
					alterarPessoaFormulario.orgaoEmissor.setValue(alterarPessoaFormulario.orgaoEmissor.getValue().substring(0,3) + alterarPessoaFormulario.uf.getValue());
					
				}
				
			}

		});
		
		alterarPessoaFormulario.uf.addValueChangeListener(e -> {

			alterarPessoaFormulario.orgaoEmissor.setValue(alterarPessoaFormulario.orgaoEmissor.getValue().substring(0,3) + alterarPessoaFormulario.uf.getValue());
				
		});

		alterarPessoaFormulario.cancelar.addClickListener(e -> {

			verificarCancelamento();

		});
		
		limitarTamanhoCampos();
		
		desabilitarCampos();
		
	}
	
	private void desabilitarCampos() {

		alterarPessoaFormulario.cpf.setReadOnly(true);
		
	}

	private void limitarTamanhoCampos() {

		alterarPessoaFormulario.cep.setMaxLength(8);
		alterarPessoaFormulario.bairro.setMaxLength(45);
		alterarPessoaFormulario.logradouro.setMaxLength(80);
		alterarPessoaFormulario.cidade.setMaxLength(45);
		alterarPessoaFormulario.cpf.setMaxLength(11);
		alterarPessoaFormulario.rg.setMaxLength(7);
		alterarPessoaFormulario.orgaoEmissor.setMaxLength(5);

	}
	
	private void verificarCancelamento() {

		PopupView popUpCancelamento = criarPopUpCancelamento("Cancelar Registro");

		popUpCancelamento.setPopupVisible(true);

		verticalLayout.addStyleName("contentCadastroView");

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
	
	private void realizarCadastroPessoa() {

		PessoaDTO cadastroDTO = montarCadastroDTO();

		try {
			
			removerPinturaCampos(alterarPessoaFormulario.bairro, alterarPessoaFormulario.cep,alterarPessoaFormulario.cidade, alterarPessoaFormulario.cpf,
					alterarPessoaFormulario.logradouro,alterarPessoaFormulario.logradouro, alterarPessoaFormulario.nome,alterarPessoaFormulario.orgaoEmissor, alterarPessoaFormulario.rg,
					alterarPessoaFormulario.estado, alterarPessoaFormulario.uf);

			pessoaService.atualizarPessoa(cadastroDTO);

			mostrarNotificacao("Cadastro Atualizado com Sucesso!", "", Notification.TYPE_HUMANIZED_MESSAGE, 5000, Position.TOP_RIGHT, FontAwesome.SMILE_O);

			getUI().getNavigator().navigateTo(Rotas.Endereco.CONSULTAR_PESSOA);

		} catch (FilaException exception) {

			mostrarNotificacao("Erro no Cadastro",exception.getMessage(), Notification.TYPE_ERROR_MESSAGE,5000,Position.TOP_RIGHT,FontAwesome.FROWN_O);

			pintarCamposVaziosTextField(alterarPessoaFormulario.bairro, alterarPessoaFormulario.cep,alterarPessoaFormulario.cidade, alterarPessoaFormulario.cpf,
					alterarPessoaFormulario.logradouro,alterarPessoaFormulario.logradouro, alterarPessoaFormulario.nome,alterarPessoaFormulario.orgaoEmissor, alterarPessoaFormulario.rg);

			pintarCamposVaziosCombobox(alterarPessoaFormulario.estado, alterarPessoaFormulario.uf);

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

		cadastroDTO.setEstadoNome(alterarPessoaFormulario.estado.getValue());
		cadastroDTO.setBairro(alterarPessoaFormulario.bairro.getValue());
		cadastroDTO.setCEP(alterarPessoaFormulario.cep.getValue());
		cadastroDTO.setCidade(alterarPessoaFormulario.cidade.getValue());
		cadastroDTO.setEstadoUF(alterarPessoaFormulario.uf.getValue());
		cadastroDTO.setEnderecoId(pessoaDTO.getEnderecoId());
		cadastroDTO.setRgId(pessoaDTO.getRgId());
		cadastroDTO.setLogradouro(alterarPessoaFormulario.logradouro.getValue());
		cadastroDTO.setPessoaCPF(alterarPessoaFormulario.cpf.getValue());
		cadastroDTO.setPessoaNome(alterarPessoaFormulario.nome.getValue());
		cadastroDTO.setRgOrgaoEmissor(alterarPessoaFormulario.orgaoEmissor.getValue());
		cadastroDTO.setRgNumero(alterarPessoaFormulario.rg.getValue());

		return cadastroDTO;

	}
	
	private void removerPinturaCampos(Object... objects) {

		for (Object object : objects) {
			
			((Component)object).removeStyleName("bordaVermelha");
			
			((Component)object).removeStyleName("bordaVermelhaCombo");
			
		}
		
	}

	public void carregarCamposPessoa(){
		
		alterarPessoaFormulario.nome.setValue(pessoaDTO.getPessoaNome() != null ? pessoaDTO.getPessoaNome() : "");
		
		alterarPessoaFormulario.cpf.setValue(pessoaDTO.getPessoaCPF() != null ? pessoaDTO.getPessoaCPF() : "");
		
		alterarPessoaFormulario.bairro.setValue(pessoaDTO.getBairro() != null ? pessoaDTO.getBairro() : "");
		
		alterarPessoaFormulario.cep.setValue(pessoaDTO.getCEP() != null ? pessoaDTO.getCEP() : "");
		
		alterarPessoaFormulario.cidade.setValue(pessoaDTO.getCidade() != null ? pessoaDTO.getCidade() : "");
		
		alterarPessoaFormulario.estado.setSelectedItem(pessoaDTO.getEstadoNome() != null ? pessoaDTO.getEstadoNome() : "");
		
		alterarPessoaFormulario.logradouro.setValue(pessoaDTO.getLogradouro() != null ? pessoaDTO.getLogradouro() : "");
		
		alterarPessoaFormulario.orgaoEmissor.setValue(pessoaDTO.getRgOrgaoEmissor() != null ? pessoaDTO.getRgOrgaoEmissor() : "");
		
		alterarPessoaFormulario.rg.setValue(pessoaDTO.getRgNumero() != null ? pessoaDTO.getRgNumero() : "");
		
		alterarPessoaFormulario.uf.setValue(pessoaDTO.getEstadoUF() != null ? pessoaDTO.getEstadoUF() : "");
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {

		verticalLayout.addComponent(VaadinTemplate.getTemplateMenu(getUI().getNavigator()));
		
		setPessoaDTO(pessoaService.consultarPorCPF(event.getParameters()));
		
		alterarPessoaView();
		
		setCompositionRoot(verticalLayout);
		
	}

	public PessoaDTO getPessoaDTO() {
		return pessoaDTO;
	}

	public void setPessoaDTO(PessoaDTO pessoaDTO) {
		this.pessoaDTO = pessoaDTO;
	}
	
}
