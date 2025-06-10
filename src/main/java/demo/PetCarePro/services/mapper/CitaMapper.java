package demo.PetCarePro.services.mapper;

import org.springframework.stereotype.Component;
import demo.PetCarePro.persistence.entities.Cita;
import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.persistence.entities.Veterinario;
import demo.PetCarePro.persistence.enumerados.TipoCita;
import demo.PetCarePro.persistence.repositories.MascotaRepository;
import demo.PetCarePro.persistence.repositories.VeterinarioRepository;
import demo.PetCarePro.services.dto.CitaRequestDTO;
import demo.PetCarePro.services.dto.CitaUrgenteDTO;

@Component
public class CitaMapper {

    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaMapper(MascotaRepository mascotaRepository, VeterinarioRepository veterinarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public Cita toEntity(CitaRequestDTO dto) {
        Cita cita = new Cita();
        
        // Asignamos los campos básicos
        cita.setFecha(dto.getFecha());
        cita.setMotivo(dto.getMotivo());
        cita.setTipoCita(TipoCita.valueOf(dto.getTipoCita())); // Suponiendo que el string recibido es "PRESENCIAL" o "TELEFONICA"

        // Buscamos la mascota por su ID y la asignamos
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el id: " + dto.getMascotaId()));
        cita.setMascota(mascota);

        // Asignamos un veterinario (en este ejemplo, se selecciona uno aleatorio)
        Veterinario veterinario = veterinarioRepository.findAleatorio()
            .orElseThrow(() -> new RuntimeException("No hay veterinarios disponibles"));
        cita.setVeterinario(veterinario);

        return cita;
    }
    
    
    
    public Cita toEntity(CitaUrgenteDTO dto) {
        Cita cita = new Cita();
        
        // Asignamos los campos básicos
        cita.setFecha(dto.getFecha());
        cita.setMotivo(dto.getMotivo());
        cita.setTipoCita(TipoCita.URGENCIA); // Suponiendo que el string recibido es "PRESENCIAL" o "TELEFONICA"

        // Buscamos la mascota por su ID y la asignamos
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el id: " + dto.getMascotaId()));
        cita.setMascota(mascota);

        // Asignamos un veterinario (en este ejemplo, se selecciona uno aleatorio)
        Veterinario veterinario = veterinarioRepository.findAleatorio()
            .orElseThrow(() -> new RuntimeException("No hay veterinarios disponibles"));
        cita.setVeterinario(veterinario);

        return cita;
    }
}

