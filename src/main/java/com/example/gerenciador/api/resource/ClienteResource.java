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
import com.example.gerenciador.api.model.Cliente;
import com.example.gerenciador.api.repository.ClienteRepository;
import com.example.gerenciador.api.service.ClienteService;

	@RestController
	@RequestMapping("/clientes")
	public class ClienteResource {
		
		@Autowired
		private ClienteRepository clienteRepository;
		
		@Autowired
		private ClienteService clienteService;
		
		@Autowired
		private ApplicationEventPublisher publisher;
		
		@GetMapping
		public List<Cliente> listar(){
			return clienteRepository.findAll();
		}
		
		@GetMapping("/{codigo}")
		public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
			Optional<Cliente> cliente = this.clienteRepository.findById(codigo);
			return cliente.isPresent()?
					ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
		}

		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
			Cliente clienteSalva = clienteRepository.save(cliente);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalva.getCodigoCliente()));
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalva) ;
		}
		
		@PutMapping("/{codigo}")
		public ResponseEntity<ResponseEntity<Cliente>> atualizar(@PathVariable Long codigo, @Valid @RequestBody Cliente cliente){
			ResponseEntity<Cliente> clienteSalva = clienteService.atualizar(codigo, cliente);
			return ResponseEntity.ok(clienteSalva);
		}
		
		@DeleteMapping("/{codigo}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void remove(@PathVariable Long codigo) {
			this.clienteRepository.deleteById(codigo);
		}
	}
