package com.example.gerenciador.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "visitas")
public class Visita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_visita")
	private Long codigoVisita;
	
	@NotNull
	@Column(name = "cd_representante")
	private Long codigoRepresentante;
	
	@NotNull
	@Column(name = "cd_cliente")
	private Long codigoCliente;
	
	@NotNull
	@Column(name = "data")
	private LocalDate data;
	
	@NotNull
	@Column(name = "valor")
	private BigDecimal valor;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
	@JoinColumn(name = "cd_representante", updatable=false, insertable=false)
	private Representante representante;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL)
	@JoinColumn(name = "cd_cliente", updatable=false, insertable=false)
	private Cliente cliente;
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getCodigoRepresentante() {
		return codigoRepresentante;
	}

	public void setCodigoRepresentante(Long codigoRepresentante) {
		this.codigoRepresentante = codigoRepresentante;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getCodigoVisita() {
		return codigoVisita;
	}

	public void setCodigoVisita(Long codigoVisita) {
		this.codigoVisita = codigoVisita;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoVisita == null) ? 0 : codigoVisita.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visita other = (Visita) obj;
		if (codigoVisita == null) {
			if (other.codigoVisita != null)
				return false;
		} else if (!codigoVisita.equals(other.codigoVisita))
			return false;
		return true;
	}
}
