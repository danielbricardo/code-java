package br.com.daniel.springrestapi.controlllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.daniel.springrestapi.entities.models.Cliente;
import br.com.daniel.springrestapi.exceptions.ResourceNotFoundException;
import br.com.daniel.springrestapi.repositories.ClienteRepository;

@Controller
@RequestMapping("/api")
public class ClienteController {
    
    @Autowired
    private ClienteRepository repository;

    @GetMapping("/clientes")
    public List<Cliente> getAll() {
        return repository.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable(value = "id") Long clientId) throws ResourceNotFoundException {
        Cliente client = repository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found on: " + clientId));
        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/clientes")
    public Cliente create(@Valid @RequestBody Cliente client) {
        return repository.save(client);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> updateUser(@PathVariable(value = "id") Long clientId, @Valid @RequestBody Cliente clientDetails) throws ResourceNotFoundException {

        Cliente oldClient = repository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found on: " + clientId));

        oldClient.setCpf(clientDetails.getCpf());
        oldClient.setNome(clientDetails.getNome());
        final Cliente updatedClient = repository.save(oldClient);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/clientes/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long clientId) throws Exception {
        Cliente client = repository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found on: " + clientId));
  
        repository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}