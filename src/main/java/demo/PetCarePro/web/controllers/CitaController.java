package demo.PetCarePro.web.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import demo.PetCarePro.persistence.entities.Cita;
import demo.PetCarePro.services.CitaService;
import demo.PetCarePro.services.dto.CitaRequestDTO;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/mes")
    public List<Cita> citasDelMes(@RequestParam int year, @RequestParam int month) {
        return citaService.obtenerCitasDelMes(year, month);
    }

    @GetMapping("/disponible")
    public boolean estaDisponible(@RequestParam String fecha) {
        LocalDateTime dateTime = LocalDateTime.parse(fecha);
        return citaService.estaDisponible(dateTime);
    }

    // Endpoint para reservar una cita usando el DTO de request
    @PostMapping("/reservar")
    public ResponseEntity<?> reservar(@RequestBody CitaRequestDTO dto) {
        try {
            Cita citaCreada = citaService.reservarCita(dto);
            return ResponseEntity.ok(citaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Endpoint para crear una cita urgente usando el DTO de request
    @PostMapping("/urgente")
    public ResponseEntity<?> crearUrgente(@RequestBody CitaRequestDTO dto) {
        try {
            Cita citaCreada = citaService.crearCitaUrgente(dto);
            return ResponseEntity.ok(citaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
