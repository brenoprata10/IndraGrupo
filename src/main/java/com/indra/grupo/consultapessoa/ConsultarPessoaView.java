package com.indra.grupo.consultapessoa;

import java.util.ArrayList;
import java.util.List;

import com.indra.grupo.entidade.Pessoa;
import com.indra.grupo.formulario.ConsultarFormulario;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
@Theme("mytheme")
public class ConsultarPessoaView implements View{

	private ConsultarFormulario consultarFormulario = new ConsultarFormulario(this);

	private Grid<Pessoa> gridPessoas = new Grid<>(Pessoa.class);

	public VerticalLayout consultarPessoaView() {
		
		consultarFormulario.nomeFiltro.addStyleName("exemplo");

		//Popular a Grid de Pessoas ========================================================================================================================================

		carregarGridPessoas(carregarLista());

		//Limpar o filtro de pesquisa quando o Botão 'Limpar é pressionado' ================================================================================================

		limparFiltro();

		//Consultar Pessoa =================================================================================================================================================

		consultarPessoa();

		//Criação dos Panels ================================================================================================================================================

		Panel panelFiltro = new Panel("Filtro de Pesquisa");
		
		panelFiltro.setResponsive(true);

		Panel panelTabelaResultado = new Panel("Resultado da Pesquisa");

		definirPanel(panelFiltro, panelTabelaResultado);

		//Título da Página =================================================================================================================================================

		Label titulo = new Label("<h2><strong>Consulta de Pessoas na Fila:</strong></h2>", ContentMode.HTML);
		
		Button atenderPessoaButton = atenderPrimeiraPessoa();
		
		VerticalLayout verticalLayout = new VerticalLayout(titulo,panelFiltro, atenderPessoaButton, panelTabelaResultado);

		//Configuração Layout ==============================================================================================================================================

		configuracaoLayout(verticalLayout,panelFiltro);
		
		verticalLayout.setStyleName("contentViewTemplate");
		
		return verticalLayout;

	}

	public void consultarPessoa(){

		consultarFormulario.consultar.addClickListener(e -> {


		});

	}
	
	public Button atenderPrimeiraPessoa(){
		
		Button atenderPessoaBotao = null;
		
		atenderPessoaBotao = new Button("Atender Primeiro da Fila", FontAwesome.USERS);
		
		atenderPessoaBotao.addStyleName("alinharDireita");
		
		atenderPessoaBotao.addClickListener(e -> {
			
			List<Pessoa> novaLista = carregarLista();
			
			Notification.show(novaLista.get(0).getCpf() + " Atendida!");
			
			novaLista.remove(0);
			
			carregarGridPessoas(novaLista);
			
		});
		
		atenderPessoaBotao.addStyleName("botaoAtender");
		
		return atenderPessoaBotao;
		
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

	public void carregarGridPessoas(List<Pessoa> pessoas){
		
		Pessoa pessoa = pessoas.get(0);
		
		gridPessoas.setItems(pessoas);

		gridPessoas.setColumns("nome", "cpf");
		
		gridPessoas.addColumn(Pessoa -> "Detalhar",new ButtonRenderer<>( e ->{
			
			consultarFormulario.nomeFiltro.setValue(((Pessoa) e.getItem()).getCpf());

		})).setWidth(136);
		
		gridPessoas.addColumn(Pessoa -> "Alterar",new ButtonRenderer<>( e ->{
			
			consultarFormulario.nomeFiltro.setValue(((Pessoa) e.getItem()).getCpf());

		})).setWidth(120);

		gridPessoas.addColumn(Pessoa -> "Excluir",new ButtonRenderer<>( e ->{

			consultarFormulario.nomeFiltro.setValue("DELETEI");
			
		})).setWidth(120);

		gridPessoas.setColumnOrder("nome", "cpf");

		gridPessoas.setCaption("Tabela de Consulta");
		
		gridPessoas.setResponsive(true);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}


