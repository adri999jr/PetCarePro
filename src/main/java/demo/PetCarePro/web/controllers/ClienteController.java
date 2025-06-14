package demo.PetCarePro.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.services.ClienteService;
import demo.PetCarePro.services.dto.ClienteDTO;
import demo.PetCarePro.services.dto.LoginRequestDTO;
import demo.PetCarePro.services.dto.LoginResponseDTO;

@RestController
@RequestMapping("/clientes")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "http://localhost:4200")

public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> list(){
		return ResponseEntity.ok(this.clienteService.findAll());
	}
	
	@GetMapping("/{idCliente}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable int idCliente) {
		if(!this.clienteService.existsCliente(idCliente)) {
			return ResponseEntity.notFound().build();
		}
		
		ClienteDTO cliente = this.clienteService.findById(idCliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	
	
	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> update(@PathVariable int idCliente, @RequestBody Cliente cliente){
		if(idCliente != cliente.getId()) {
			return ResponseEntity.badRequest().build();
		}
		else if(!this.clienteService.existsCliente(idCliente)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.clienteService.save(cliente));
	}
	
	@DeleteMapping("/{idCliente}")
	public ResponseEntity<Cliente> delete(@PathVariable int idCliente){
		if(this.clienteService.delete(idCliente)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
	    try {
	        LoginResponseDTO response = clienteService.login(request);
	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(401).body(e.getMessage());
	    }
	}

	
	@GetMapping("/username/{username}")
	public ResponseEntity<Cliente> getClienteByUsername(@PathVariable String username) {
	    Optional<Cliente> clienteOpt = clienteService.getClienteByUsername(username);
	    return clienteOpt
	        .map(ResponseEntity::ok)
	        .orElseGet(() -> ResponseEntity.notFound().build());
	}


}
