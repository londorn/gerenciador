package com.example.gerenciador.api.model;

import java.math.BigDecimal;

public class VisitasTotais {
	
	private BigDecimal valorTotal = new BigDecimal("0.00");
	
	private String nomeRepresentante; 

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public String getNomeRepresentante() {
		return nomeRepresentante;
	}

	public void setNomeRepresentante(String nomeRepresentante) {
		this.nomeRepresentante = nomeRepresentante;
	}
}
