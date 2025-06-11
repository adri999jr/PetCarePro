package demo.PetCarePro.web.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.services.MascotaService;
import demo.PetCarePro.services.dto.MascotaDTO;
import demo.PetCarePro.services.mapper.MascotaMapper;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    // Obtener todas las mascotas
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> list() {
        List<MascotaDTO> mascotasDTO = mascotaService.findAll()
                .stream()
                .map(MascotaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mascotasDTO);
    }

    // Obtener una mascota por su ID
    @GetMapping("/{idMascota}")
    public ResponseEntity<MascotaDTO> findById(@PathVariable int idMascota) {
        if (!mascotaService.existsMascota(idMascota)) {
            return ResponseEntity.notFound().build();
        }
        MascotaDTO mascotaDTO = mascotaService.findById(idMascota);
        return ResponseEntity.ok(mascotaDTO);
    }

    // Crear una nueva mascota (puede lanzar ResourceNotFoundException si el cliente no existe)
    @PostMapping
    public ResponseEntity<MascotaDTO> create(@RequestBody Mascota mascota) {
        Mascota mascotaCreada = mascotaService.create(mascota);
        return new ResponseEntity<>(MascotaMapper.toDTO(mascotaCreada), HttpStatus.CREATED);
    }

    // Actualizar una mascota existente
    @PutMapping("/{idMascota}")
    public ResponseEntity<MascotaDTO> update(@PathVariable int idMascota, @RequestBody Mascota mascota) {
        if (idMascota != mascota.getId_mascota()) {
            return ResponseEntity.badRequest().build();
        } else if (!mascotaService.existsMascota(idMascota)) {
            return ResponseEntity.notFound().build();
        }
        Mascota mascotaActualizada = mascotaService.save(mascota);
        return ResponseEntity.ok(MascotaMapper.toDTO(mascotaActualizada));
    }

    // Eliminar una mascota
    @DeleteMapping("/{idMascota}")
    public ResponseEntity<Void> delete(@PathVariable int idMascota) {
        if (mascotaService.delete(idMascota)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/cliente")
    public ResponseEntity<List<Mascota>> getMascotasCliente(Authentication authentication) {
        String username = authentication.getName(); // Extrae el username del token JWT
        Optional<List<Mascota>> mascotasOptional = mascotaService.getMascotasByClienteUsername(username);
        
        return mascotasOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    
    @GetMapping("/cliente/{username}")
    public ResponseEntity<List<Mascota>> getMascotasByClienteUsername(@PathVariable String username) {
         Optional<List<Mascota>> mascotasOpt = mascotaService.getMascotasByClienteUsername(username);
         return mascotasOpt
             .map(ResponseEntity::ok)
             .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    
    @PutMapping("/{id}/historial")
    public ResponseEntity<?> actualizarHistorialMedico(@PathVariable int id, @RequestBody String nuevoHistorial) {
        Optional<Mascota> mascotaOpt = mascotaService.findEntityById(id);
        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            mascota.setHistorial_medico(nuevoHistorial);
            mascotaService.save(mascota);
            // Devuelve un objeto JSON con un mensaje
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Historial actualizado"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "Mascota no encontrada"));
    }

    
    @GetMapping("/buscar")
    public ResponseEntity<List<Mascota>> buscarPorNombre(@RequestParam String nombre) {
        Optional<List<Mascota>> resultado = mascotaService.buscarPorNombre(nombre);
        return resultado.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
