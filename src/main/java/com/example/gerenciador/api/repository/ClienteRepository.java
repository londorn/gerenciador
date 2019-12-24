package com.example.gerenciador.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gerenciador.api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
}
