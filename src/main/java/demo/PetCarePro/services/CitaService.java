package demo.PetCarePro.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import demo.PetCarePro.persistence.entities.Cita;
import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.persistence.entities.Veterinario;
import demo.PetCarePro.persistence.enumerados.TipoCita;
import demo.PetCarePro.persistence.repositories.CitaRepository;
import demo.PetCarePro.persistence.repositories.MascotaRepository;
import demo.PetCarePro.persistence.repositories.VeterinarioRepository;
import demo.PetCarePro.services.dto.CitaRequestDTO;
import demo.PetCarePro.services.mapper.CitaMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final CitaMapper citaMapper; // Inyectamos el mapper

    // 1. Obtener citas ocupadas en un día específico
    public List<LocalDateTime> obtenerCitasOcupadas(LocalDate fecha) {
        return citaRepository.findByFechaBetween(
            fecha.atStartOfDay(),
            fecha.atTime(LocalTime.MAX)
        ).stream()
         .map(Cita::getFecha)
         .collect(Collectors.toList());
    }

   

    public Cita reservarCita(CitaRequestDTO dto) {
        // Convertimos el DTO en una entidad Cita usando el mapper
        Cita cita = citaMapper.toEntity(dto);

        // Verificamos la disponibilidad de la franja horaria
        if (!estaDisponible(cita.getFecha())) {
            throw new RuntimeException("Esa franja ya está ocupada.");
        }

        // Asignamos automáticamente un veterinario si no viene en el DTO
        if (cita.getVeterinario() == null) {
            Veterinario veterinario = veterinarioRepository.findAleatorio()
                .orElseThrow(() -> new RuntimeException("No hay veterinarios disponibles"));
            cita.setVeterinario(veterinario);
        }

        return citaRepository.save(cita);
    }


    // 4. Crear cita de urgencia como veterinario usando parámetros individuales (legacy)
    public Cita crearCitaUrgencia(Integer idMascota, Integer idVeterinario, LocalDateTime fecha, String motivo) {
        Mascota mascota = mascotaRepository.findById(idMascota)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Veterinario veterinario = veterinarioRepository.findById(idVeterinario)
            .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        Cita cita = new Cita();
        cita.setFecha(fecha);
        cita.setMotivo(motivo);
        cita.setTipoCita(TipoCita.URGENCIA);
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        return citaRepository.save(cita);
    }

    // 5. Crear cita de urgencia usando el DTO (para veterinario)
    public Cita crearCitaUrgente(CitaRequestDTO dto) {
        Cita cita = citaMapper.toEntity(dto);
        // Forzamos que el tipo de cita sea URGENCIA, sin tomar el valor del DTO
        cita.setTipoCita(TipoCita.URGENCIA);
        return citaRepository.save(cita);
    }

    // 6. Obtener citas por cliente
    public Optional<List<Cita>> obtenerCitasCliente(String usernameCliente) {
        return citaRepository.findByMascotaClienteUsername(usernameCliente);
    }

    // 7. Obtener citas por veterinario
    public List<Cita> obtenerCitasVeterinario(String usernameVeterinario) {
        return citaRepository.findByVeterinarioUsername(usernameVeterinario);
    }
    
    // 8. Obtener citas del mes
    public List<Cita> obtenerCitasDelMes(int year, int month) {
        LocalDateTime inicio = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime fin = inicio.plusMonths(1);
        return citaRepository.findByFechaBetween(inicio, fin);
    }

    // 9. Verificar si una fecha está disponible
    public boolean estaDisponible(LocalDateTime fecha) {
        return !citaRepository.existsByFecha(fecha);
    }

    // 10. Reservar una cita (método simplificado que recibe la entidad Cita)
    public Cita reservarCita(Cita cita) {
        if (!estaDisponible(cita.getFecha())) {
            throw new RuntimeException("Esa franja ya está ocupada.");
        }
        return citaRepository.save(cita);
    }
    

    // 11. Crear cita urgente (método simplificado que recibe la entidad Cita)
    public Cita crearCitaUrgente(Cita cita) {
        return citaRepository.save(cita); // sin restricciones de disponibilidad
    }
    
   
    
    
    
  
   
}
