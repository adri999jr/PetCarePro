package demo.PetCarePro.services.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CitaUrgenteDTO {

	private LocalDateTime fecha;
    private String motivo;
    private Integer mascotaId;
    private String tipoCita;
    private Integer veterinarioId;
}
