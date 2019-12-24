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
import com.example.gerenciador.api.model.Representante;
import com.example.gerenciador.api.model.RepresentanteVisitas;
import com.example.gerenciador.api.repository.RepresentanteRepository;
import com.example.gerenciador.api.service.RepresentanteService;

@RestController
@RequestMapping("/representantes")
public class RepresentanteResource {
	
	@Autowired
	private RepresentanteRepository representanteRepository;
	
	@Autowired
	private RepresentanteService representanteService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Representante> listar(){
		return (List<Representante>) representanteRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Representante> representante = this.representanteRepository.findById(codigo);
		return representante.isPresent()?
				ResponseEntity.ok(representante.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/totalVisitas")
	public ResponseEntity<RepresentanteVisitas> totalVisitas() {
		RepresentanteVisitas representanteVisitas = this.representanteService.totalVisitas(this.representanteRepository.findAll());
		return ResponseEntity.ok(representanteVisitas);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Representante> cadastrar(@Valid @RequestBody Representante representante, HttpServletResponse response) {
		Representante representanteSalva = representanteRepository.save(representante);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, representanteSalva.getCodigoRepresentante()));
		return ResponseEntity.status(HttpStatus.CREATED).body(representanteSalva) ;
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ResponseEntity<Representante>> atualizar(@PathVariable Long codigo, @Valid @RequestBody Representante representante){
		ResponseEntity<Representante> representanteSalva = representanteService.atualizar(codigo, representante);
		return ResponseEntity.ok(representanteSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long codigo) {
		this.representanteRepository.deleteById(codigo);
	}

}
