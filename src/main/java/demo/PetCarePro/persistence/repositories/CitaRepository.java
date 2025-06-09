package demo.PetCarePro.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import demo.PetCarePro.persistence.entities.Cita;
import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.persistence.entities.Veterinario;

public interface CitaRepository extends ListCrudRepository<Cita, Integer>{
	 List<Cita> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

	    boolean existsByFecha(LocalDateTime fecha);

//	    @Query("SELECT c FROM Cita c WHERE c.mascota.cliente.username = :username")
//	    List<Cita> findByMascotaClienteUsername(@Param("username") String username);

	    @Query("SELECT c FROM Cita c WHERE c.veterinario.username = :username")
	    List<Cita> findByVeterinarioUsername(@Param("username") String username);
	    
	    
	    Optional<List<Cita>> findByMascotaClienteUsername(String username);
	    
}
