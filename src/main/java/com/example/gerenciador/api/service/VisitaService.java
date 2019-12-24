package com.example.gerenciador.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.gerenciador.api.model.Visita;
import com.example.gerenciador.api.model.VisitasTotais;
import com.example.gerenciador.api.repository.VisitaRepository;

@Service
public class VisitaService {

	@Autowired
	VisitaRepository visitaRepository;

	public ResponseEntity<Visita> atualizar(Long codigo, Visita visita) {
		Visita visitaSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(visita, visitaSalva, "codigo");
		visitaRepository.save(visitaSalva);
		return ResponseEntity.ok(visitaSalva);
	}

	public Visita buscarPessoaPeloCodigo(Long codigo) {
		Visita visitaSalva = this.visitaRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return visitaSalva;
	}
	
	public VisitasTotais totalValores(List<Visita> visitas) {
		
		List<VisitasTotais> visitasTotais = new ArrayList<>();
		Map<Long, List<Visita>> mapProd = visitas.parallelStream()
				.collect(Collectors.groupingBy(Visita::getCodigoRepresentante));
		
		for (Entry<Long, List<Visita>> entryProd : mapProd.entrySet()) {
			VisitasTotais visitaOrdenada = new VisitasTotais();
			entryProd.getValue().forEach(v ->  {
				visitaOrdenada.setNomeRepresentante(v.getRepresentante().getNome());
				visitaOrdenada.setValorTotal(visitaOrdenada.getValorTotal().add(v.getValor())); 
			});
			visitasTotais.add(visitaOrdenada);
		}
		
		visitasTotais.sort((VisitasTotais v1, VisitasTotais v2) -> v2.getValorTotal().compareTo(v1.getValorTotal()));
		
		return visitasTotais.parallelStream().findFirst().get();
	}
}
