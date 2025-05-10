package demo.PetCarePro.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.services.MascotaService;
import demo.PetCarePro.services.dto.MascotaDTO;
import demo.PetCarePro.services.mapper.MascotaMapper;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

	@Autowired
	private MascotaService mascotaService;

	@GetMapping
	public ResponseEntity<List<MascotaDTO>> list() {
		List<MascotaDTO> mascotasDTO = this.mascotaService.findAll().stream().map(MascotaMapper::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(mascotasDTO);
	}

	// Consultar una mascota por id
	@GetMapping("/{idMascota}")
	public ResponseEntity<MascotaDTO> findById(@PathVariable int idMascota) {
		if (!this.mascotaService.existsMascota(idMascota)) {
			return ResponseEntity.notFound().build();
		}
		MascotaDTO mascotaDTO = this.mascotaService.findById(idMascota);
		return ResponseEntity.ok(mascotaDTO);
	}

	
	@PostMapping
	public ResponseEntity<MascotaDTO> create(@RequestBody Mascota mascota) {
		Mascota mascotaCreada = this.mascotaService.create(mascota);
		return new ResponseEntity<>(MascotaMapper.toDTO(mascotaCreada), HttpStatus.CREATED);
	}

	
	@PutMapping("/{idMascota}")
	public ResponseEntity<MascotaDTO> update(@PathVariable int idMascota, @RequestBody Mascota mascota) {
		if (idMascota != mascota.getId_mascota()) {
			return ResponseEntity.badRequest().build();
		} else if (!this.mascotaService.existsMascota(idMascota)) {
			return ResponseEntity.notFound().build();
		}
		Mascota mascotaActualizada = this.mascotaService.save(mascota);
		return ResponseEntity.ok(MascotaMapper.toDTO(mascotaActualizada));
	}

	@DeleteMapping("/{idMascota}")
	public ResponseEntity<Void> delete(@PathVariable int idMascota) {
		if (this.mascotaService.delete(idMascota)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}