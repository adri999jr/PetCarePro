package demo.PetCarePro.services;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.persistence.repositories.ClienteRepository;
import demo.PetCarePro.persistence.repositories.MascotaRepository;
import demo.PetCarePro.services.dto.MascotaDTO;
import demo.PetCarePro.services.mapper.MascotaMapper;


@Service
public class MascotaService {
    
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired 
    ClienteRepository clienteRepository;
    
    
    public List<Mascota> findAll() {
        return this.mascotaRepository.findAll();
    }
    public Optional<List<Mascota>> getMascotasByClienteUsername(String username) {
        return mascotaRepository.findByClienteUsername(username);
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
    
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String mensaje) {
            super(mensaje);
        }
    }

    public Mascota create(Mascota mascota) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(mascota.getIdCliente());
        if (!clienteOpt.isPresent()) {
            throw new ResourceNotFoundException("Cliente no encontrado con id " + mascota.getIdCliente());
        }
        return mascotaRepository.save(mascota);
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