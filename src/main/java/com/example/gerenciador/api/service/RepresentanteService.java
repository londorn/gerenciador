package com.example.gerenciador.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.gerenciador.api.model.Representante;
import com.example.gerenciador.api.model.RepresentanteVisitas;
import com.example.gerenciador.api.repository.RepresentanteRepository;

@Service
public class RepresentanteService {
	
	@Autowired
	private RepresentanteRepository representanteRepository;

	public ResponseEntity<Representante> atualizar(Long codigo, Representante representante) {
		Representante representanteSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(representante, representanteSalva, "codigo");
		representanteRepository.save(representanteSalva);
		return ResponseEntity.ok(representanteSalva);
	}

	public Representante buscarPessoaPeloCodigo(Long codigo) {
		Representante representanteSalva = this.representanteRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return representanteSalva;
	}

	
	public RepresentanteVisitas totalVisitas(List<Representante> representantes) {
		RepresentanteVisitas representanteVisitas  = new RepresentanteVisitas();
		
		representantes.sort((Representante r1, Representante r2)->r2.getVisita().size()-r1.getVisita().size());
		
		Representante representante = representantes.parallelStream().findFirst().get();
		
		representanteVisitas.setQtdVisitass(representante.getVisita().size());
		representanteVisitas.setNomeRepresentante(representante.getNome());
		
		return representanteVisitas;
	}
	
	
}
