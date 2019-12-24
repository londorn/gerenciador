package com.example.gerenciador.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "representantes")
public class Representante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_representante")
	private Long codigoRepresentante;
	
	@NotNull
	@Column(name = "nome")
	private String nome;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "representante")
	private List<Visita> visita;
	
	public Long getCodigoRepresentante() {
		return codigoRepresentante;
	}

	public void setCodigoRepresentante(Long codigoRepresentante) {
		this.codigoRepresentante = codigoRepresentante;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Visita> getVisita() {
		return visita;
	}

	public void setVisita(List<Visita> visita) {
		this.visita = visita;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoRepresentante == null) ? 0 : codigoRepresentante.hashCode());
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
		Representante other = (Representante) obj;
		if (codigoRepresentante == null) {
			if (other.codigoRepresentante != null)
				return false;
		} else if (!codigoRepresentante.equals(other.codigoRepresentante))
			return false;
		return true;
	}

}