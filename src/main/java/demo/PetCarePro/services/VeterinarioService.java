package demo.PetCarePro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.PetCarePro.persistence.entities.Veterinario;
import demo.PetCarePro.persistence.repositories.VeterinarioRepository;
@Service
public class VeterinarioService {
	@Autowired
    private VeterinarioRepository veterinarioRepository;
    
    // Devuelve la lista de todos los veterinarios
    public List<Veterinario> findAll() {
        return this.veterinarioRepository.findAll();
    }
    
    // Verifica si existe un veterinario con el id proporcionado
    public boolean existsVeterinario(int idVeterinario) {
        return this.veterinarioRepository.existsById(idVeterinario);
    }
    
    // Devuelve la entidad Veterinario envuelta en un Optional
    public Optional<Veterinario> findEntityById(int idVeterinario) {
        return this.veterinarioRepository.findById(idVeterinario);
    }
    
    // Devuelve la entidad Veterinario (se asume que el id existe, de lo contrario se lanzará una excepción)
    public Veterinario findById(int idVeterinario) {
        return this.veterinarioRepository.findById(idVeterinario).get();
    }
    
    // Crea un nuevo Veterinario
    public Veterinario create(Veterinario veterinario) {
        return this.veterinarioRepository.save(veterinario);
    }
    
    // Actualiza o guarda un Veterinario
    public Veterinario save(Veterinario veterinario) {
        return this.veterinarioRepository.save(veterinario);
    }
    
    // Elimina un Veterinario por su id
    public boolean delete(int idVeterinario) {
        boolean result = false;
        if (this.veterinarioRepository.existsById(idVeterinario)) {
            this.veterinarioRepository.deleteById(idVeterinario);
            result = true;
        }
        return result;
    }
}
