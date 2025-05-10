package demo.PetCarePro.persistence.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;

import demo.PetCarePro.persistence.enumerados.Especie;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_mascota;
    
    @Column(name = "id_cliente")
	private Integer idCliente;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Especie especie;

    @Column(length = 50)
    private String raza;

    @Column(nullable = false)
    private String fecha_nacimiento;

    @Column(length = 500)
    private String historial_medico;

    @ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
	private Cliente cliente;
}
