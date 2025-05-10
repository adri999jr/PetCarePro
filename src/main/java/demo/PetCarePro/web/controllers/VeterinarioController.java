package demo.PetCarePro.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {
	@Autowired
    private VeterinarioService veterinarioService;
    
    // Endpoint para listar todos los veterinarios
    @GetMapping
    public ResponseEntity<List<Veterinario>> list() {
        List<Veterinario> veterinarios = this.veterinarioService.findAll();
        return ResponseEntity.ok(veterinarios);
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
    
    // Endpoint para crear un nuevo veterinario
    @PostMapping
    public ResponseEntity<Veterinario> create(@RequestBody Veterinario veterinario) {
        Veterinario veterinarioCreado = this.veterinarioService.create(veterinario);
        return new ResponseEntity<>(veterinarioCreado, HttpStatus.CREATED);
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
}
