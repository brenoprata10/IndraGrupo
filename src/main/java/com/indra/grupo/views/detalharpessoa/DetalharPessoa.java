package com.indra.grupo.views.detalharpessoa;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class DetalharPessoa extends HorizontalLayout {
	protected TextField nome;
	protected TextField cpf;
	protected TextField rg;
	protected TextField orgaoEmissor;
	protected NativeSelect<java.lang.String> uf;
	protected TextField cep;
	protected TextField logradouro;
	protected TextField bairro;
	protected TextField cidade;
	protected NativeSelect<java.lang.String> estado;

	public DetalharPessoa() {
		Design.read(this);
	}
}
