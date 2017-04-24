package com.indra.grupo.enums;

public enum SenhaEnum {

	ATIVA(1),DESATIVA(2);
	
	private SenhaEnum(Integer status) {
		
		setStatusSenha(status);
		
	}

	private Integer statusSenha;

	public Integer getStatusSenha() {
		return statusSenha;
	}

	public void setStatusSenha(Integer statusSenha) {
		this.statusSenha = statusSenha;
	}
	
}
