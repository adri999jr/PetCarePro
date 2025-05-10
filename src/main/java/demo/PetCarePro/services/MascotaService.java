package demo.PetCarePro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.persistence.repositories.MascotaRepository;
import demo.PetCarePro.services.dto.MascotaDTO;
import demo.PetCarePro.services.mapper.MascotaMapper;

@Service
public class MascotaService {
    
    @Autowired
    private MascotaRepository mascotaRepository;
    
    
    public List<Mascota> findAll() {
        return this.mascotaRepository.findAll();
    }
    
    
    public boolean existsMascota(int idMascota) {
        return this.mascotaRepository.existsById(idMascota);
    }
    
    
    public Optional<Mascota> findEntityById(int idMascota) {
        return this.mascotaRepository.findById(idMascota);
    }
    
    
    public MascotaDTO findById(int idMascota) {
        Mascota mascota = this.mascotaRepository.findById(idMascota)
                            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + idMascota));
        return MascotaMapper.toDTO(mascota);
    }
    
    
    public Mascota create(Mascota mascota) {
        return this.mascotaRepository.save(mascota);
    }
    
   
    public Mascota save(Mascota mascota) {
        return this.mascotaRepository.save(mascota);
    }
    
    
    public boolean delete(int idMascota) {
        if(this.mascotaRepository.existsById(idMascota)) {
            this.mascotaRepository.deleteById(idMascota);
            return true;
        }
        return false;
    }
}