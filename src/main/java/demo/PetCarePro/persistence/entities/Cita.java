package demo.PetCarePro.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import demo.PetCarePro.persistence.enumerados.TipoCita;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cita {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false, length = 200)
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "fk_mascota", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "fk_veterinario", nullable = false)
    private Veterinario veterinario;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCita tipoCita;
}
