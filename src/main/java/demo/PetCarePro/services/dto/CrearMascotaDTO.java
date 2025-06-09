package demo.PetCarePro.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrearMascotaDTO {
    private String usernameCliente;  // En lugar de idCliente
    private String nombre;
    private String especie;
    private String raza;
    private String fechaNacimiento;
    private String historialMedico;

}

