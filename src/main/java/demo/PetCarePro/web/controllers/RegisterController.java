package demo.PetCarePro.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.persistence.entities.Veterinario;
import demo.PetCarePro.services.ClienteService;
import demo.PetCarePro.services.VeterinarioService;


@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VeterinarioService veterinarioService;
   // @CrossOrigin(origins = "http://localhost:4200/register/cliente")
    @PostMapping("/cliente")
    public ResponseEntity<Cliente> registerCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.registerCliente(cliente));
    }

    @PostMapping("/veterinario")
    public ResponseEntity<Veterinario> registerVeterinario(@RequestBody Veterinario veterinario) {
        return ResponseEntity.ok(veterinarioService.registerVeterinario(veterinario));
    }
}

