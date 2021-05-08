package br.com.daniel.springrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.daniel.springrestapi.entities.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> { }
