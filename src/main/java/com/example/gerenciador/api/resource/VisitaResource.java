package com.example.gerenciador.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciador.api.event.RecursoCriadoEvent;
import com.example.gerenciador.api.model.Visita;
import com.example.gerenciador.api.model.VisitasTotais;
import com.example.gerenciador.api.repository.VisitaRepository;
import com.example.gerenciador.api.service.VisitaService;

@RestController
@RequestMapping("/visitas")
public class VisitaResource {

	@Autowired
	private VisitaRepository visitaRepository;
	
	@Autowired
	private VisitaService visitaService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Visita> listar() {
		return visitaRepository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<?> lancamento = this.visitaRepository.findById(codigo);
		return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping("/totalValores")
	public ResponseEntity<VisitasTotais> totalValores() {
		VisitasTotais visitasTotais =  this.visitaService.totalValores(this.visitaRepository.findAll());
		return ResponseEntity.ok(visitasTotais);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Visita> cadastrar(@Valid @RequestBody Visita visita, HttpServletResponse response) {
		Visita visitaSalva = visitaRepository.save(visita);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, visitaSalva.getCodigoVisita()));
		return ResponseEntity.status(HttpStatus.CREATED).body(visitaSalva);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ResponseEntity<Visita>> atualizar(@PathVariable Long codigo, @Valid @RequestBody Visita visita){
		ResponseEntity<Visita> visitaSalva = visitaService.atualizar(codigo, visita);
		return ResponseEntity.ok(visitaSalva);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long codigo) {
		this.visitaRepository.deleteById(codigo);
	}
}
