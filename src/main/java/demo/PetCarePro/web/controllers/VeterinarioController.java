package demo.PetCarePro.web.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.PetCarePro.persistence.entities.Veterinario;
import demo.PetCarePro.services.VeterinarioService;
import demo.PetCarePro.services.dto.LoginRequestDTO;
import demo.PetCarePro.services.dto.LoginResponseDTO;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {
	@Autowired
    private VeterinarioService veterinarioService;
    
    // Endpoint para listar todos los veterinarios
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Veterinario>> getTodosVeterinariosSinAdmin() {
        List<Veterinario> vets = veterinarioService.obtenerTodosSinAdmins();
        return ResponseEntity.ok(vets);
    }
    
    // Endpoint para obtener un veterinario por su ID
    @GetMapping("/{idVeterinario}")
    public ResponseEntity<Veterinario> findById(@PathVariable int idVeterinario) {
        if (!this.veterinarioService.existsVeterinario(idVeterinario)) {
            return ResponseEntity.notFound().build();
        }
        Veterinario veterinario = this.veterinarioService.findById(idVeterinario);
        return ResponseEntity.ok(veterinario);
    }
    
 
    // Endpoint para actualizar un veterinario existente. Se verifica que el ID de la URL coincida con el del objeto
    @PutMapping("/{idVeterinario}")
    public ResponseEntity<Veterinario> update(@PathVariable int idVeterinario, @RequestBody Veterinario veterinario) {
        if (idVeterinario != veterinario.getId()) {
            return ResponseEntity.badRequest().build();
        } else if (!this.veterinarioService.existsVeterinario(idVeterinario)) {
            return ResponseEntity.notFound().build();
        }
        Veterinario veterinarioActualizado = this.veterinarioService.save(veterinario);
        return ResponseEntity.ok(veterinarioActualizado);
    }
    
    // Endpoint para eliminar un veterinario por su ID
    @DeleteMapping("/{idVeterinario}")
    public ResponseEntity<Void> delete(@PathVariable int idVeterinario) {
        if (this.veterinarioService.delete(idVeterinario)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO response = veterinarioService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    
    @PutMapping("/validar/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> alternarValidacionVeterinario(@PathVariable String username) {
        Optional<Veterinario> optionalVeterinario = veterinarioService.getVeterinarioByUsername(username);
        if (optionalVeterinario.isPresent()) {
            Veterinario vet = optionalVeterinario.get();
            vet.setValidado(!vet.isValidado()); // alterna el valor
            veterinarioService.save(vet); // guarda el cambio
            return ResponseEntity.ok().body(Map.of("validado", vet.isValidado()));
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/pendientes")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Veterinario>> listarNoValidados() {
        return ResponseEntity.ok(veterinarioService.findNoValidados());
    }
    

    @GetMapping("/username/{username}")
    public ResponseEntity<Veterinario> getVeterinarioByUsername(@PathVariable String username) {
        Optional<Veterinario> veterinarioOpt = veterinarioService.getVeterinarioByUsername(username);
        return veterinarioOpt
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    


}
