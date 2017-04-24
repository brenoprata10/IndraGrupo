package com.indra.grupo.views.detalharpessoa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.indra.grupo.dto.PessoaDTO;
import com.indra.grupo.enums.EstadoEnum;
import com.indra.grupo.formulario.DetalharFormulario;
import com.indra.grupo.modelo.Estado;
import com.indra.grupo.modelo.Pessoa;
import com.indra.grupo.rotas.Rotas;
import com.indra.grupo.services.PessoaService;
import com.indra.grupo.template.VaadinTemplate;
import com.indra.grupo.views.consultapessoa.ConsultarPessoaView;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@CDIView(value = Rotas.Endereco.DETALHAR_PESSOA)
public class DetalharPessoaView extends CustomComponent implements View{

	@Inject
	private PessoaService pessoaService;
	
	private DetalharFormulario detalharPessoaFormulario = new DetalharFormulario(this);
	
	private VerticalLayout verticalLayout = new VerticalLayout();
	
	private PessoaDTO pessoaDTO;
	
	public void detalharPessoaView(){
		
		carregarTela();
		
		Button retornarConsulta = new Button("Voltar Para Consulta", FontAwesome.ARROW_LEFT);
		
		retornarConsulta.setStyleName("botaoVoltarConsulta");
		
		retornarConsulta.addClickListener(e ->{
			
			getUI().getNavigator().navigateTo(Rotas.Endereco.CONSULTAR_PESSOA);
			
		});
		
		CssLayout cssLayout = new CssLayout(retornarConsulta);
		
		Label titulo = new Label("<h2><strong>Detalhar Pessoa:</strong></h2>", ContentMode.HTML);
		
		Panel panelCadastro = new Panel("Detalhar Formulário");
		
		carregarCamposPessoa();
		
		desabilitarCampos();
		
		panelCadastro.setContent(detalharPessoaFormulario);
		
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

		detalharPessoaFormulario.estado.setItems(estadosString);

		detalharPessoaFormulario.uf.setItems(ufsString);
		
	}

	public void carregarCamposPessoa(){
		
		detalharPessoaFormulario.nome.setValue(pessoaDTO.getPessoaNome() != null ? pessoaDTO.getPessoaNome() : "");
		
		detalharPessoaFormulario.cpf.setValue(pessoaDTO.getPessoaCPF() != null ? pessoaDTO.getPessoaCPF() : "");
		
		detalharPessoaFormulario.bairro.setValue(pessoaDTO.getBairro() != null ? pessoaDTO.getBairro() : "");
		
		detalharPessoaFormulario.cep.setValue(pessoaDTO.getCEP() != null ? pessoaDTO.getCEP() : "");
		
		detalharPessoaFormulario.cidade.setValue(pessoaDTO.getCidade() != null ? pessoaDTO.getCidade() : "");
		
		detalharPessoaFormulario.estado.setSelectedItem(pessoaDTO.getEstadoNome() != null ? pessoaDTO.getEstadoNome() : "");
		
		detalharPessoaFormulario.logradouro.setValue(pessoaDTO.getLogradouro() != null ? pessoaDTO.getLogradouro() : "");
		
		detalharPessoaFormulario.orgaoEmissor.setValue(pessoaDTO.getRgOrgaoEmissor() != null ? pessoaDTO.getRgOrgaoEmissor() : "");
		
		detalharPessoaFormulario.rg.setValue(pessoaDTO.getRgNumero() != null ? pessoaDTO.getRgNumero() : "");
		
		detalharPessoaFormulario.uf.setValue(pessoaDTO.getEstadoUF() != null ? pessoaDTO.getEstadoUF() : "");
		
	}
	
	public void desabilitarCampos(){
		
		detalharPessoaFormulario.nome.setReadOnly(true);
		
		detalharPessoaFormulario.cpf.setReadOnly(true);
		
		detalharPessoaFormulario.bairro.setReadOnly(true);
		
		detalharPessoaFormulario.cep.setReadOnly(true);
		
		detalharPessoaFormulario.cidade.setReadOnly(true);
		
		detalharPessoaFormulario.estado.setReadOnly(true);
		
		detalharPessoaFormulario.logradouro.setReadOnly(true);
		
		detalharPessoaFormulario.orgaoEmissor.setReadOnly(true);
		
		detalharPessoaFormulario.rg.setReadOnly(true);
		
		detalharPessoaFormulario.uf.setReadOnly(true);
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {

		verticalLayout.addComponent(VaadinTemplate.getTemplateMenu(getUI().getNavigator()));
		
		setPessoaDTO(pessoaService.consultarPorCPF(event.getParameters()));
		
		detalharPessoaView();
		
		setCompositionRoot(verticalLayout);
		
	}

	public PessoaDTO getPessoaDTO() {
		return pessoaDTO;
	}

	public void setPessoaDTO(PessoaDTO pessoaDTO) {
		this.pessoaDTO = pessoaDTO;
	}
	
}
