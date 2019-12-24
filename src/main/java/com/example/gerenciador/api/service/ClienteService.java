package com.example.gerenciador.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.gerenciador.api.model.Cliente;
import com.example.gerenciador.api.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;

	public ResponseEntity<Cliente> atualizar(Long codigo, Cliente cliente) {
		Cliente clienteSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(cliente, clienteSalva, "codigo");
		clienteRepository.save(clienteSalva);
		return ResponseEntity.ok(clienteSalva);
	}

	public Cliente buscarPessoaPeloCodigo(Long codigo) {
		Cliente clienteSalva = this.clienteRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return clienteSalva;
	}

}
